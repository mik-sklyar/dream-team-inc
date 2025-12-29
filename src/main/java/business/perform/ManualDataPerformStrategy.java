package business.perform;

import business.EmployeeOperationStrategy;
import data.CustomLinkedList;
import data.Employee;
import presentation.EmployeeNumberPrompt;
import presentation.Utils;

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
    public ManualDataPerformStrategy(Consumer<CustomLinkedList<Employee>> callback) {
        super(callback);
    }

    /**
     * Основной метод, выполняющий операцию по сбору данных о сотрудниках.
     *
     * @return Список созданных сотрудников.
     */
    @Override
    protected CustomLinkedList<Employee> performOperation() {
        System.out.println(Utils.veryWhiteString("\n=== РУЧНОЙ ВВОД СОТРУДНИКОВ ==="));

        Scanner scanner = new Scanner(System.in);

        EmployeeNumberPrompt numberPrompt = new EmployeeNumberPrompt(
                "Введите количество сотрудников для ввода (или 0 для выхода): ",
                scanner
        );

        int arrayLength = numberPrompt.getCount();

        if (arrayLength == 0) {
            System.out.println("Отмена операции. Возврат в предыдущее меню.");
            return null;
        }

        CustomLinkedList<Employee> employees = new CustomLinkedList<>();
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
}
