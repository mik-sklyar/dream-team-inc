import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ManualDataPerformStrategy implements ActionStrategy {
    private List<Employee> employees;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int arrayLength = getArrayLength(scanner);
        this.employees = new ArrayList<>(arrayLength);

        System.out.println("\nВведите данные для " + arrayLength + " сотрудников.");

        for (int i = 0; i < arrayLength; ) {
            System.out.println("\n--- Ввод данных для сотрудника #" + (i + 1) + " ---");
            try {
                Employee.Builder builder = new Employee.Builder();

                System.out.print("Введите имя: ");
                builder.setName(scanner.nextLine());

                System.out.print("Введите email: ");
                builder.setEmail(scanner.nextLine());

                System.out.print("Введите пароль (не менее 6 символов): ");
                builder.setPassword(scanner.nextLine());

                employees.add(builder.build());
                System.out.println("Сотрудник №" + (i + 1) + " успешно добавлен.");
                i++;

            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте ввести данные для этого сотрудника еще раз.");
            }
        }

        saveEmployeesToFile();
    }

    private void saveEmployeesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("employees.txt"))) {
            for (Employee employee : employees) {
                writer.println(employee.toString());
            }
            System.out.println("\nСписок сотрудников успешно сохранен в файл employees.txt");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }

    private int getArrayLength(Scanner scanner) {
        int length;
        while (true) {
            System.out.print("Введите количество сотрудников для ввода: ");
            String line = scanner.nextLine();
            try {
                length = Integer.parseInt(line);
                if (length > 0) {
                    return length;
                } else {
                    System.out.println("Ошибка: количество должно быть больше нуля.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введено не число. Пожалуйста, попробуйте снова.");
            }
        }
    }
}
