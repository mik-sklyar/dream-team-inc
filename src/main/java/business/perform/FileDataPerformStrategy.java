package business.perform;

import data.Employee;
import data.perform.EmployeeFileReader;
import business.EmployeeOperationStrategy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class FileDataPerformStrategy extends EmployeeOperationStrategy {


    public FileDataPerformStrategy(Consumer<List<Employee>> callback) {
        super(callback);
    }

    @Override
    protected List<Employee> performOperation() {
        Scanner scanner = new Scanner(System.in);

        EmployeeFileReader fileReader = new EmployeeFileReader();

        System.out.println("\n=== ЗАГРУЗКА СОТРУДНИКОВ ИЗ ФАЙЛА ===");

        System.out.print("Введите количество сотрудников для загрузки (0 - выйти): ");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine());
            if (count < 0) {
                System.out.println("Ошибка: число должно быть неотрицательным");
                return new ArrayList<>();
            }
            if (count == 0) {
                System.out.println("Возвращаемся в меню...");
                return new ArrayList<>();
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите целое число");
            return new ArrayList<>();
        }

        System.out.println("Введите имя файла или 0 для выхода в меню");
        System.out.print(">> ");
        String filename = scanner.nextLine().trim();

        if (filename.equals("0")) {
            System.out.println("Возвращаемся в меню...");
            return new ArrayList<>();
        }

        if (filename.isEmpty()) {
            System.out.println("Ошибка: имя файла не может быть пустым");
            return new ArrayList<>();
        }

        File file = fileReader.findFile(filename);

        if (file == null || !file.exists()) {
            System.out.println("Ошибка: файл '" + filename + "' не найден");
            System.out.println("Поместите файл в папку resources/ проекта");
            return new ArrayList<>();
        }

        try {

            List<Employee> allEmployees = fileReader.readEmployeesFromFile(file);

            if (allEmployees.isEmpty()) {
                System.out.println("Ошибка: в файле нет корректных данных");
                return new ArrayList<>();
            }

            List<Employee> selectedEmployees;
            if (count <= allEmployees.size()) {
                selectedEmployees = allEmployees.subList(0, count);
                System.out.println("Загружено: " + count + " сотрудников из файла");
            } else {
                selectedEmployees = allEmployees;
                System.out.println("В файле только " + allEmployees.size() +
                        " сотрудников. Загружены все.");
            }

            return selectedEmployees;

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
