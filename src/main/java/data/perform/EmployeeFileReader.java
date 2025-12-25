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

    private final EmployeeFileReader fileReader;
    private final Scanner scanner;

    public FileDataPerformStrategy(Consumer<List<Employee>> callback) {
        super(callback);
        this.fileReader = new EmployeeFileReader();
        this.scanner = new Scanner(System.in);
    }

    @Override
    protected List<Employee> performOperation() {
        System.out.println("\n=== ЗАГРУЗКА СОТРУДНИКОВ ИЗ ФАЙЛА ===");
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
            List<Employee> employees = fileReader.readEmployeesFromFile(file);

            if (employees.isEmpty()) {
                System.out.println("Ошибка: в файле нет корректных данных");
                return new ArrayList<>();
            }

            System.out.println("Успешно загружено: " + employees.size() + " сотрудников");
            return employees;

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
