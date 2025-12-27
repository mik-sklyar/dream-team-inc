package business.sorting;

import business.EmployeeOperationStrategy;
import data.Employee;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static data.Employee.SortingFields;

public class EmployeeSortXStrategy extends EmployeeOperationStrategy {
    final protected SortingFields sortingField = SortingFields.EMAIL;
    final protected SortingFields filterField = SortingFields.ORDER;
    final protected boolean filterByEven = true;

    public EmployeeSortXStrategy(List<Employee> input, Consumer<List<Employee>> callback) {
        super(input, callback);
    }

    @Override
    protected List<Employee> performOperation() {
        if (inputData == null || inputData.isEmpty()) {
            return inputData;
        }
//        inputData.stream().filter(it -> it.getOrder() % 2 == 0).forEach(System.out::println);
        LinkedList<Employee> mutableList = new LinkedList<>(this.inputData);
        quickSort(mutableList, 0, mutableList.size() - 1);
//        System.out.println("----");
//        mutableList.stream().filter(it -> it.getOrder() % 2 == 0).forEach(System.out::println);
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
        // Находим первый элемент удовлетворяющий условию фильтрации с конца
        int pivotIndex = high;
        while (pivotIndex >= low && !isSortableEmployee(list.get(pivotIndex))) {
            pivotIndex--;
        }
        if (pivotIndex < low) return high;

        Employee pivot = list.get(pivotIndex);
        Comparable pivotField = sortingField.getMethod().apply(pivot);
        int i = low - 1;
        for (int j = low; j < pivotIndex; j++) {
            // Обрабатываем только элементы удовлетворяющие условию фильтрации
            if (isSortableEmployee(list.get(j))) {
                if (sortingField.getMethod().apply(list.get(j)).compareTo(pivotField) <= 0) {
                    i++;
                    // Пропускаем элементы не удовлетворяющие
                    while (i < j && !isSortableEmployee(list.get(i))) {
                        i++;
                    }
                    // И наконец меняем местами подходящие элементы
                    if (i != j && isSortableEmployee(list.get(i))) {
                        swap(list, i, j);
                    }
                }
            }
        }
        // Находим позицию для опорного элемента
        i++;
        while (i < pivotIndex && !isSortableEmployee(list.get(i))) {
            i++;
        }
        // Меняем местами опорный элемент
        if (i != pivotIndex) {
            swap(list, i, pivotIndex);
        }
        return i;
    }

    private long getFilterField(Employee em) {
        Object obj = filterField.getMethod().apply(em);
        if (obj.getClass() == Integer.class) {
            return ((Integer) obj).longValue();
        }
        return (Long) obj;
    }
    private boolean isSortableEmployee(Employee em) {
        long field = getFilterField(em);
        return (field % 2 == 0) == filterByEven;
    }

    private void swap(List<Employee> list, int i, int j) {
        Employee temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
