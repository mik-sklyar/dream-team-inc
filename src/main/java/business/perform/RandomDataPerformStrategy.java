package business.perform;

import business.EmployeeOperationStrategy;
import data.Employee;
import presentation.EmployeeNumberPrompt;
import java.util.function.Consumer;

import java.util.*;

/**
 * Реализует стратегию создания случайного количества сотрудников со случайными данными.
 */
public class RandomDataPerformStrategy extends EmployeeOperationStrategy {

    public RandomDataPerformStrategy(Consumer<List<Employee>> callback) {
        super(callback);
    }

    @Override
    protected List<Employee> performOperation() {
        System.out.println("\n=== ГЕНЕРАЦИЯ СЛУЧАЙНЫХ СОТРУДНИКОВ ===");

        EmployeeNumberPrompt numberPrompt = new EmployeeNumberPrompt(
                "Введите количество сотрудников для создания (или 0 для выхода): "
        );

        int count = numberPrompt.getCount();
        if (count == 0) {
            System.out.println("Отмена операции. Возврат в предыдущее меню.");
            return Collections.emptyList();
        }

        List<Employee> employees = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            employees.add(generateRandomEmployee());
        }
        return employees;
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
