package business.perform;

import business.EmployeeOperationStrategy;
import data.Employee;

import java.util.*;
import java.util.function.Consumer;

/**
 * Реализует стратегию создания случайного количества сотрудников со случайными данными.
 */
public class RandomDataPerformStrategy extends EmployeeOperationStrategy {

    public RandomDataPerformStrategy(Consumer<List<Employee>> callback) {
        super(callback);
    }

    @Override
    protected List<Employee> performOperation() {
        int count = getEmployeeCount();
        List<Employee> employees = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            employees.add(generateRandomEmployee());
        }
        return employees;
    }

    private int getEmployeeCount() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите количество случайных сотрудников для создания: ");
            try {
                int count = Integer.parseInt(scanner.nextLine());
                if (count > 0) {
                    return count;
                } else {
                    System.out.println("Количество должно быть больше нуля.");
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите целое число.");
            }
        }
    }

    private Employee generateRandomEmployee() {
        String randomUUID = UUID.randomUUID().toString();
        return new Employee.Builder()
                .setName("Random " + randomUUID.substring(0, 8))
                .setEmail(randomUUID.substring(9, 13) + "@random.com")
                .setPassword(randomUUID.substring(14, 23))
                .build();
    }
}
