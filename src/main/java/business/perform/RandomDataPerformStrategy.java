package business.perform;

import business.EmployeeOperationStrategy;
import data.Employee;
import data.perform.RandomDataEmployeesProvider;
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
                "Введите количество сотрудников для призыва (или 0 для выхода): "
        );

        int count = numberPrompt.getCount();
        if (count == 0) {
            System.out.println("Отмена операции. Возврат в предыдущее меню.");
            return Collections.emptyList();
        }

        System.out.println("Богиня жизни \"Гея\" призвала новых сотрудников");
        return RandomDataEmployeesProvider.provideRandomEmployees(count);
    }
}
