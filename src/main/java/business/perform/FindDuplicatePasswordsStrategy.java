package business.perform;

import business.EmployeeOperationStrategy;
import data.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Стратегия для многопоточного поиска повторяющихся паролей
 */
public class FindDuplicatePasswordsStrategy extends EmployeeOperationStrategy {

    public FindDuplicatePasswordsStrategy(List<Employee> input) {
        super(input, null);
    }

    @Override
    protected List<Employee> performOperation() {
        System.out.println("\n--- Поиск одинаковых паролей в 2 потоках ---");

        if (inputData == null || inputData.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
        } else {
            printDuplicateResults(findDuplicatePasswordsTwoThreads());
        }
        return null;
    }

    private Map<String, List<String>> findDuplicatePasswordsTwoThreads() {
        int middle = inputData.size() / 2;
        List<Employee> firstHalf = inputData.subList(0, middle);
        List<Employee> secondHalf = inputData.subList(middle, inputData.size());
        ConcurrentHashMap<String, List<String>> passwordMap = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
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
        return passwordMap;
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


    private void printDuplicateResults(Map<String, List<String>> map) {
        long duplicatesCount = map.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .peek(entry -> {
            List<String> names = entry.getValue();

            System.out.printf("%nПароль '%s' используется %d сотрудниками:%n", entry.getKey(), names.size());
            names.forEach(name -> System.out.println("  " + name));
        }).count();

        if (duplicatesCount == 0) {
            System.out.println("\nВсё хорошо: сотрудники используют уникальные пароли.");
        }
    }
}
