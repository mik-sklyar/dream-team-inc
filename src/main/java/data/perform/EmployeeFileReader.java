package data.perform;

import data.CustomLinkedList;
import data.Employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class EmployeeFileReader {

    public CustomLinkedList<Employee> readEmployeesFromFile(File file, int count) {
        var stat = new Object() {
            final AtomicInteger lineNumber = new AtomicInteger(0);
            final AtomicInteger successfulCount = new AtomicInteger(0);
            final AtomicInteger errorCount = new AtomicInteger(0);
        };
        CustomLinkedList<Employee> employees = new CustomLinkedList<>();

        System.out.println("\n---- Начинаем чтение файла ----");
        System.out.println("-------------------------------");

        try (Stream<String> lines = new BufferedReader(new FileReader(file)).lines()) {
            //noinspection ResultOfMethodCallIgnored
            lines.anyMatch(line -> {
                stat.lineNumber.getAndIncrement();
                if (line.isBlank() || line.startsWith("#")) {
                    return false;
                }
                try {
                    String[] parts = line.split("[;,\\t]");
                    if (parts.length != 3) {
                        throw new IllegalStateException("неверный формат");
                    }
                    Employee employee = new Employee.Builder()
                            .setName(parts[0].trim())
                            .setEmail(parts[1].trim())
                            .setPassword(parts[2].trim())
                            .build();
                    employees.add(employee);
                    stat.successfulCount.getAndIncrement();
                    if (stat.successfulCount.intValue() == count) {
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка в строке " + stat.lineNumber + ": " + e.getMessage());
                    System.out.println("Содержимое строки: " + line);
                    stat.errorCount.getAndIncrement();
                }
                return false;
            });
        } catch (IOException e) {
            System.err.println("Файл не найден: " + e);
        }

        System.out.println("\n--- Статистика чтения файла ---");
        System.out.println("Успешно распознано: " + stat.successfulCount + " сотрудников");
        System.out.println("Ошибок при чтении: " + stat.errorCount + " строк");
        System.out.println("Всего обработано строк: " + stat.lineNumber);
        System.out.println("-------------------------------");
        return employees;
    }

    public File findFile(String filename) {
        File file = new File(filename);

        if (file.exists() && file.isFile()) {
            System.out.println("Файл найден по указанному пути: " + file.getAbsolutePath());
            return file;
        }

        String[] possiblePaths = {
                "src/main/resources/" + filename,
                "resources/" + filename,
                "src/resources/" + filename,
                filename
        };

        System.out.println("Поиск файла в проекте...");

        for (String path : possiblePaths) {
            file = new File(path);
            if (file.exists() && file.isFile()) {
                System.out.println("Файл найден: " + file.getAbsolutePath());
                return file;
            }
        }

        System.out.println("Файл не найден в проекте: " + filename);
        return null;
    }
}
