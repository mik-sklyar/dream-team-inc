package business.sorting;

import business.EmployeeOperationStrategy;
import data.Employee;
import data.Employee.SortingFields;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class EmployeeSortStrategy extends EmployeeOperationStrategy {
    final protected SortingFields sortingField;

    public EmployeeSortStrategy(List<Employee> input, SortingFields sortingField, Consumer<List<Employee>> callback) {
        super(input, callback);
        this.sortingField = sortingField;
    }

    @Override
    protected List<Employee> performOperation() {
        LinkedList<Employee> mutableList = new LinkedList<>(this.inputData);
        quickSort(mutableList, 0, mutableList.size() - 1);
        return List.copyOf(mutableList);
    }

    private void quickSort(List<Employee> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private int partition(List<Employee> list, int low, int high) {
        Comparable pivot = sortingField.getMethod().apply(list.get(high));
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (sortingField.getMethod().apply(list.get(j)).compareTo(pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private void swap(List<Employee> list, int i, int j) {
        Employee temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
