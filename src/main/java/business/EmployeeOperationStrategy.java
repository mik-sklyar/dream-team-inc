package business;

import data.CustomLinkedList;
import data.Employee;

import java.util.function.Consumer;

public abstract class EmployeeOperationStrategy implements ActionStrategy {
    protected final CustomLinkedList<Employee> inputData;
    private final Consumer<CustomLinkedList<Employee>> callback;

    public EmployeeOperationStrategy(CustomLinkedList<Employee> input, Consumer<CustomLinkedList<Employee>> callback) {
        this.inputData = input;
        this.callback = callback;
    }

    public EmployeeOperationStrategy(Consumer<CustomLinkedList<Employee>> callback) {
        this(null, callback);
    }

    public EmployeeOperationStrategy() {
        this(null);
    }

    @Override
    public void execute() {
        CustomLinkedList<Employee> result = performOperation();
        if (callback != null) {
            callback.accept(result);
        }
    }

    protected abstract CustomLinkedList<Employee> performOperation();

}
