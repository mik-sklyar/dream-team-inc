package business.sorting;

import business.EmployeeOperationStrategy;
import data.CustomLinkedList;
import data.Employee;

import java.util.function.Consumer;

import static business.sorting.EmployeeQuickSorter.SortingFields;

public class EmployeeSortStrategy extends EmployeeOperationStrategy {
    final protected SortingFields sortingField;

    public EmployeeSortStrategy(CustomLinkedList<Employee> input,
                                EmployeeQuickSorter.SortingFields sortingField,
                                Consumer<CustomLinkedList<Employee>> callback) {
        super(input, callback);
        this.sortingField = sortingField;
    }

    @Override
    protected CustomLinkedList<Employee> performOperation() {
        return new EmployeeQuickSorter(sortingField, true, null).sortEmployees(inputData);
    }
}
