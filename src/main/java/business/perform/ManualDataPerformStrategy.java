package business.perform;

import business.EmployeeOperationStrategy;
import data.Employee;
import presentation.EmployeeNumberPrompt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Реализует стратегию для ручного ввода данных о сотрудниках через консоль.
 */
public class ManualDataPerformStrategy extends EmployeeOperationStrategy {

    /**
     * Конструктор для стратегии ручного ввода.
     */
    public ManualDataPerformStrategy(Consumer<List<Employee>> callback) {
        super(callback);
    }

    /**
     * Основной метод, выполняющий операцию по сбору данных о сотрудниках.
     */
    @Override
    protected List<Employee> performOperation() {
        System.out.println("\n=== РУЧНОЙ ВВОД СОТРУДНИКОВ ===");

        Scanner scanner = new Scanner(System.in);

        // Создаем EmployeeNumberPrompt локально в методе
        EmployeeNumberPrompt numberPrompt = new EmployeeNumberPrompt(
                "Введите количество сотрудников для ввода (или 0 для выхода): "
        );

        int arrayLength = numberPrompt.getCount();

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

        System.out.println("\nУспешно добавлено " + employees.size() + " сотрудников.");
        return employees;
    }

    /**
     * Запрашивает у пользователя ввод данных до тех пор, пока они не пройдут валидацию.
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
                System.out.println("Ошибка валидации: " + e.getMessage());
            }
        }
    }
}
