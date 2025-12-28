package business.perform;

import business.EmployeeOperationStrategy;

import data.CustomLinkedList;
import business.EmployeeOperationStrategy;
import data.Employee;
import data.perform.EmployeeFileReader;
import presentation.EmployeeNumberPrompt;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;

public class FileDataPerformStrategy extends EmployeeOperationStrategy {


    public FileDataPerformStrategy(Consumer<CustomLinkedList<Employee>> callback) {
        super(callback);
    }

    @Override
    protected CustomLinkedList<Employee> performOperation() {
        System.out.println("\n=== ЗАГРУЗКА СОТРУДНИКОВ ИЗ ФАЙЛА ===");

        EmployeeNumberPrompt numberPrompt = new EmployeeNumberPrompt(
                "Введите количество сотрудников для загрузки (или 0 для выхода): "
        );

        int count = numberPrompt.getCount();
        if (count == 0) {
            System.out.println("Отмена операции. Возврат в предыдущее меню.");
            return Collections.emptyList();
        }

        String filename;
        while (true) {
            System.out.println("Введите имя файла или 0 для выхода в меню");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            filename = scanner.nextLine().trim();
            if (filename.isEmpty()) {
                System.out.println("Ошибка: имя файла не может быть пустым");
                continue;
            }
            break;
        }
        if (filename.equals("0")) {
            System.out.println("Отмена операции. Возврат в предыдущее меню.");
            return new CustomLinkedList<>();
        }

        EmployeeFileReader fileReader = new EmployeeFileReader();
        File file = fileReader.findFile(filename);

        if (file == null || !file.exists()) {
            System.out.println("Ошибка: файл '" + filename + "' не найден");
            System.out.println("Поместите файл в папку resources/ проекта");
            return new CustomLinkedList<>();
        }

        try {
            CustomLinkedList<Employee> employees = fileReader.readEmployeesFromFile(file, count);

            if (employees.isEmpty()) {
                System.out.println("Ошибка: в файле нет корректных данных");
                return new CustomLinkedList<>();
            }
            if (employees.size() >= count) {
                System.out.println("Загружено: " + count + " сотрудников из файла");
            } else {
                System.out.println("В файле только " + employees.size() + " сотрудников. Загружены все.");
            }
            return employees;

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return new CustomLinkedList<>();
        }
    }
}
