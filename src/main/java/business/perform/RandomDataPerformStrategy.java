package business.perform;

import business.EmployeeOperationStrategy;
import data.CustomLinkedList;
import data.Employee;
import data.perform.RandomDataEmployeesProvider;
import presentation.EmployeeNumberPrompt;
import java.util.function.Consumer;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Реализует стратегию создания случайного количества сотрудников со случайными данными.
 */
public class RandomDataPerformStrategy extends EmployeeOperationStrategy {

    public RandomDataPerformStrategy(Consumer<CustomLinkedList<Employee>> callback) {
        super(callback);
    }

    @Override
    protected CustomLinkedList<Employee> performOperation() {
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
