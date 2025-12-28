package business.sorting;

import business.EmployeeOperationStrategy;
import data.CustomLinkedList;
import data.Employee;

import java.util.function.Consumer;

import static business.sorting.EmployeeQuickSorter.FilterConfiguration;
import static business.sorting.EmployeeQuickSorter.FilterFields.FILTER_ORDER;
import static business.sorting.EmployeeQuickSorter.SortingFields.EMAIL;

public class EmployeeSortXStrategy extends EmployeeOperationStrategy {

    public EmployeeSortXStrategy(CustomLinkedList<Employee> input, Consumer<CustomLinkedList<Employee>> callback) {
        super(input, callback);
    }

    @Override
    protected CustomLinkedList<Employee> performOperation() {
        FilterConfiguration config = new FilterConfiguration(FILTER_ORDER, value -> value % 2 == 0);
        EmployeeQuickSorter sorter = new EmployeeQuickSorter(EMAIL, true, config);

//        inputData.stream().filter(it -> it.getOrder() % 2 == 0).forEach(System.out::println);
        var list = sorter.sortEmployees(inputData);
//        System.out.println("---");
//        list.stream().filter(it -> it.getOrder() % 2 == 0).forEach(System.out::println);
        return list;
    }
}
