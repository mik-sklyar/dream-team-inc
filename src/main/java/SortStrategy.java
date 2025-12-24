import java.util.List;
import java.util.function.Consumer;

/**
 * Абстрактный базовый класс для всех стратегий сортировки сотрудников.
 */
public abstract class SortStrategy extends EmployeeOperationStrategy {

    public SortStrategy(List<Employee> input, Consumer<List<Employee>> callback) {
        super(input, callback);
    }
}
