package business.perform;

import business.EmployeeOperationStrategy;
import data.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Реализует стратегию для ручного ввода данных о сотрудниках через консоль.
 * Пользователю последовательно предлагается ввести данные для каждого сотрудника.
 * В случае ошибки ввода для какого-либо поля, запрос повторяется до корректного ввода.
 */
public class ManualDataPerformStrategy extends EmployeeOperationStrategy {

    /**
     * Конструктор для стратегии ручного ввода.
     *
     * @param callback Функция, которая будет вызвана с результатом операции (списком сотрудников).
     */
    public ManualDataPerformStrategy(Consumer<List<Employee>> callback) {
        super(callback);
    }

    /**
     * Основной метод, выполняющий операцию по сбору данных о сотрудниках.
     *
     * @return Список созданных сотрудников.
     */
    @Override
    protected List<Employee> performOperation() {

        Scanner scanner = new Scanner(System.in);
        int arrayLength = getArrayLength(scanner);

        if (arrayLength == 0) {
            System.out.println("Отмена операции. Возврат в предыдущее меню.");
            return Collections.emptyList();
        }

        List<Employee> employees = new ArrayList<>(arrayLength);
        System.out.println("\nВведите данные для " + arrayLength + " сотрудников.");

        for (int i = 0; i < arrayLength; ) {
            System.out.println("\n--- Ввод данных для сотрудника №" + (i + 1) + " ---");
            Employee.Builder builder = new Employee.Builder();

            inputValidField(scanner, "Введите имя: ", value -> {
                builder.setName(value);
                return true;
            });

            inputValidField(scanner, "Введите email: ", value -> {
                builder.setEmail(value);
                return true;
            });

            inputValidField(scanner, "Введите пароль (не менее 6 символов): ", value -> {
                builder.setPassword(value);
                return true;
            });

            try {
                employees.add(builder.build());
                i++;
                System.out.println("Сотрудник №" + i + " успешно добавлен.");
            } catch (IllegalStateException e) {
                System.out.println("Ошибка при создании сотрудника: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте ввести данные для этого сотрудника еще раз.");
            }
        }
        return employees;
    }

    /**
     * Запрашивает у пользователя ввод данных до тех пор, пока они не пройдут валидацию.
     *
     * @param scanner   Экземпляр Scanner для чтения ввода.
     * @param prompt    Сообщение для пользователя.
     * @param validator Функция-валидатор, которая применяет значение.
     */
    private void inputValidField(Scanner scanner, String prompt, Function<String, Boolean> validator) {
        while (true) {
            try {
                System.out.print(prompt);
                String value = scanner.nextLine();
                if (validator.apply(value)) {
                    return;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                // Ловим исключения валидации из сеттеров и просим пользователя повторить ввод.
                System.out.println("Ошибка валидации: " + e.getMessage());
            }
        }
    }

    /**
     * Запрашивает у пользователя количество сотрудников для ввода.
     *
     * @param scanner Экземпляр Scanner для чтения ввода.
     * @return Количество сотрудников или 0 для отмены.
     */
    private int getArrayLength(Scanner scanner) {
        int length;
        while (true) {
            System.out.print("Введите количество сотрудников для ввода (или 0 для выхода): ");
            String line = scanner.nextLine();
            try {
                length = Integer.parseInt(line);
                if (length >= 0) {
                    return length; // Возвращаем корректное значение.
                } else {
                    System.out.println("Ошибка: количество не может быть отрицательным.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введено не число. Пожалуйста, попробуйте снова.");
            }
        }
    }
}
