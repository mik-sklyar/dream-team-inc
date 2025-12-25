package business;

import data.Employee;

import java.util.List;
import java.util.function.Consumer;

public abstract class EmployeeOperationStrategy implements ActionStrategy {
    protected final List<Employee> inputData;
    private final Consumer<List<Employee>> callback;

    public EmployeeOperationStrategy(List<Employee> input, Consumer<List<Employee>> callback) {
        this.inputData = input;
        this.callback = callback;
    }

    public EmployeeOperationStrategy(Consumer<List<Employee>> callback) {
        this(null, callback);
    }

    public EmployeeOperationStrategy() {
        this(null);
    }

    @Override
    public void execute() {
        List<Employee> result = performOperation();
        if (callback != null) {
            callback.accept(result);
        }
    }

    protected abstract List<Employee> performOperation();

}
