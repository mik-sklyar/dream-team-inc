package business.sorting;

import data.CustomLinkedList;
import data.Employee;

import java.util.function.Function;


public class EmployeeQuickSorter {
    private final SortingFields sortingField;

    private final boolean acceding;
    private final FilterConfiguration filterConfig;

    public EmployeeQuickSorter(SortingFields sortingField, boolean acceding, FilterConfiguration filterConfig) {
        this.sortingField = sortingField;
        this.acceding = acceding;
        this.filterConfig = filterConfig;
    }


    public CustomLinkedList<Employee> sortEmployees(CustomLinkedList<Employee> inputData) {
        if (inputData == null || inputData.isEmpty()) {
            return inputData;
        }
        CustomLinkedList<Employee> list = new CustomLinkedList<>(inputData);
        if (this.filterConfig == null) {
            quickSort(list, 0, list.size() - 1);

        } else {
            quickSortFiltered(list, 0, list.size() - 1);
        }
        return list;
    }

    private void swap(CustomLinkedList<Employee> list, int i, int j) {
        Employee temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
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
            if ((sortingField.getMethod().apply(list.get(j)).compareTo(pivot) <= 0) == acceding) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }


    private void quickSortFiltered(CustomLinkedList<Employee> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionFiltered(list, low, high);
            quickSortFiltered(list, low, pivotIndex - 1);
            quickSortFiltered(list, pivotIndex + 1, high);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private int partitionFiltered(CustomLinkedList<Employee> list, int low, int high) {
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
                if ((sortingField.getMethod().apply(list.get(j)).compareTo(pivotField) <= 0) == acceding) {
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
        Object obj = filterConfig.field.getMethod().apply(em);
        if (obj.getClass() == Integer.class) {
            return ((Integer) obj).longValue();
        }
        return (Long) obj;
    }

    private boolean isSortableEmployee(Employee em) {
        long field = getFilterField(em);
        return filterConfig.condition.apply(field);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    public enum SortingFields {
        ORDER("order", Employee::getOrder, "Порядковый номер сотрудника"),
        ID("id", Employee::getId, "Уникальный идентификатор"),
        NAME("name", Employee::getName, "Имя"),
        EMAIL("email", Employee::getEmail, "Электронная почта"),
        PASSWORD("password", Employee::getPassword, "Пароль");

        private final String key;
        private final String description;
        private final Function<Employee, Comparable> method;

        SortingFields(String key, Function<Employee, Comparable> method, String description) {
            this.key = key;
            this.method = method;
            this.description = description;
        }

        public String getKey() {
            return key;
        }

        public Function<Employee, Comparable> getMethod() {
            return method;
        }

        @Override
        public String toString() {
            return this.description;
        }
    }

    @SuppressWarnings({"rawtypes", "unused"})
    public enum FilterFields {
        FILTER_ORDER(SortingFields.ORDER),
        FILTER_ID(SortingFields.ID);

        private final SortingFields sortingField;

        FilterFields(SortingFields field) {
            this.sortingField = field;
        }

        public String getKey() {
            return sortingField.key;
        }

        public Function<Employee, Comparable> getMethod() {
            return sortingField.method;
        }

        @Override
        public String toString() {
            return this.sortingField.description;
        }
    }

    public static class FilterConfiguration {
        private final FilterFields field;
        private final Function<Long, Boolean> condition;

        public FilterConfiguration(FilterFields field, Function<Long, Boolean> condition) {
            this.field = field;
            this.condition = condition;
        }
    }
}
