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
            // x
        }
        return list;
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

    public class FilterConfiguration {
        private final FilterFields filterField;
        private final Function<Long, Boolean> condition;

        public FilterConfiguration(FilterFields filterField, Function<Long, Boolean> condition) {
            this.filterField = filterField;
            this.condition = condition;
        }
    }
}
