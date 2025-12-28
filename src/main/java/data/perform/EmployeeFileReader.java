package data.perform;

import data.CustomLinkedList;
import data.Employee;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFileReader {

    public CustomLinkedList<Employee> readEmployeesFromFile(File file, int count) throws IOException {
        CustomLinkedList<Employee> employees = new CustomLinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            int successfulCount = 0;
            int errorCount = 0;

            System.out.println("Начинаем чтение файла: " + file.getAbsolutePath());
            System.out.println("--------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("[;,\\t]");

                if (parts.length != 3) {
                    System.out.println("Ошибка в строке " + lineNumber +
                            ": неверный формат. Ожидается: Имя;email;пароль");
                    System.out.println("   Содержимое строки: " + line);
                    errorCount++;
                    continue;
                }

                try {
                    Employee employee = new Employee.Builder()
                            .setName(parts[0].trim())
                            .setEmail(parts[1].trim())
                            .setPassword(parts[2].trim())
                            .build();
                    employees.add(employee);
                    successfulCount++;
                    if (successfulCount == count) {
                        break;
                    }

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                    System.out.println("   Содержимое строки: " + line);
                    errorCount++;
                }
            }

            System.out.println("--------------------------------------------------");
            System.out.println("СТАТИСТИКА ЧТЕНИЯ ФАЙЛА:");
            System.out.println("   Успешно распознано: " + successfulCount + " сотрудников");
            System.out.println("   Ошибок при чтении: " + errorCount + " строк");
            System.out.println("   Всего обработано строк: " + lineNumber);
            System.out.println("--------------------------------------------------");

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            throw e;
        }

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
