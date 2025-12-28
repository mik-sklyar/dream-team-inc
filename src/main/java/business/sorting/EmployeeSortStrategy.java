package business.sorting;

import business.EmployeeOperationStrategy;

import data.CustomLinkedList;
import data.Employee;
import data.Employee.SortingFields;

import java.util.function.Consumer;

public class EmployeeSortStrategy extends EmployeeOperationStrategy {
    final protected SortingFields sortingField;

    public EmployeeSortStrategy(CustomLinkedList<Employee> input, SortingFields sortingField, Consumer<CustomLinkedList<Employee>> callback) {
        super(input, callback);
        this.sortingField = sortingField;
    }

    @Override
    protected CustomLinkedList<Employee> performOperation() {
        CustomLinkedList<Employee> mutableList = new CustomLinkedList<>();
        CustomLinkedList<Employee> copy = new CustomLinkedList<>();
        mutableList.addAll(this.inputData);
        quickSort(mutableList, 0, mutableList.size() - 1);
        copy.addAll(mutableList);
        return copy;
    }

    private void quickSort(CustomLinkedList<Employee> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private int partition(CustomLinkedList<Employee> list, int low, int high) {
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

    private void swap(CustomLinkedList<Employee> list, int i, int j) {
        Employee temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
