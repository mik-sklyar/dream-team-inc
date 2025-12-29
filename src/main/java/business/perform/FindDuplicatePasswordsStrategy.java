package business.perform;

import business.EmployeeOperationStrategy;
import data.Employee;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Стратегия для многопоточного поиска повторяющихся паролей
 */
public class FindDuplicatePasswordsStrategy extends EmployeeOperationStrategy {

    public FindDuplicatePasswordsStrategy(List<Employee> input, Consumer<List<Employee>> callback) {
        super(input, callback);
    }

    @Override
    protected List<Employee> performOperation() {
        System.out.println("\n=== МНОГОПОТОЧНЫЙ ПОИСК ПОВТОРЯЮЩИХСЯ ПАРОЛЕЙ ===");
        
        if (inputData == null || inputData.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return Collections.emptyList();
        }

        System.out.println("Обработка " + inputData.size() + " сотрудников в 2 потоках...");
        

        Map<String, List<String>> passwordToNamesMap = findDuplicatePasswordsTwoThreads();

        
        printDuplicateResults(passwordToNamesMap);

        return inputData;
    }

    private Map<String, List<String>> findDuplicatePasswordsTwoThreads() {
        ConcurrentHashMap<String, List<String>> passwordMap = new ConcurrentHashMap<>();
        
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        
        
        int middle = inputData.size() / 2;
        List<Employee> firstHalf = inputData.subList(0, middle);
        List<Employee> secondHalf = inputData.subList(middle, inputData.size());
        
        
        executorService.submit(() -> processBatch(firstHalf, passwordMap, "Поток 1"));
        executorService.submit(() -> processBatch(secondHalf, passwordMap, "Поток 2"));
        
        executorService.shutdown();
        
        try {
            
            boolean finished = executorService.awaitTermination(30, TimeUnit.SECONDS);
            if (!finished) {
                System.out.println("Предупреждение: поиск занял больше времени, чем ожидалось.");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Поиск был прерван.");
            executorService.shutdownNow();
        }
        
        System.out.println("\nОба потока завершили работу. Анализируем результаты...");
        
        
        ConcurrentHashMap<String, List<String>> duplicates = new ConcurrentHashMap<>();
        passwordMap.forEach((password, names) -> {
            if (names.size() > 1) {
                duplicates.put(password, names);
            }
        });
        
        return duplicates;
    }


    private void processBatch(List<Employee> batch, ConcurrentHashMap<String, List<String>> passwordMap, String threadName) {
        Thread.currentThread().setName(threadName);
        System.out.println(threadName + ": начал обработку " + batch.size() + " сотрудников");
        
        for (Employee employee : batch) {
            String password = employee.getPassword();
            String name = employee.getName();
            
            passwordMap.compute(password, (key, existingNames) -> {
                if (existingNames == null) {
                    List<String> newList = new ArrayList<>();
                    newList.add(name);
                    return newList;
                } else {
                    existingNames.add(name);
                    return existingNames;
                }
            });
        }
        
        System.out.println(threadName + ": завершил обработку");
    }

     
    private void printDuplicateResults(Map<String, List<String>> passwordToNamesMap) {
        if (passwordToNamesMap.isEmpty()) {
            System.out.println("\nПовторяющихся паролей не найдено.");
            System.out.println("Все сотрудники используют уникальные пароли.");
            return;
        }

        System.out.println("\n=== РЕЗУЛЬТАТЫ ПОИСКА ДУБЛИКАТОВ ПАРОЛЕЙ ===");
        System.out.println("Найдено " + passwordToNamesMap.size() + " повторяющихся паролей:");
        System.out.println("─".repeat(50));
        
        int totalDuplicates = 0;
        int index = 1;
        
        for (Map.Entry<String, List<String>> entry : passwordToNamesMap.entrySet()) {
            String password = entry.getKey();
            List<String> names = entry.getValue();
            int duplicateCount = names.size();
            totalDuplicates += duplicateCount;
            
            System.out.printf("%d. Пароль: '%s'%n", index++, password);
            System.out.printf("   Используется %d сотрудниками:%n", duplicateCount);
            
            for (int i = 0; i < names.size(); i++) {
                System.out.printf("    %d. %s%n", i + 1, names.get(i));
            }
            System.out.println();
        }
        
        
    }
}
