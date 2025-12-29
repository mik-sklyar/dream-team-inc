package business.sorting;

import business.EmployeeOperationStrategy;
import data.CustomLinkedList;
import data.Employee;
import presentation.SortingConfigurationPrompt;

import java.util.function.Consumer;
import java.util.function.Function;

import static business.sorting.EmployeeQuickSorter.FilterConfiguration;

public class EmployeeSortXStrategy extends EmployeeOperationStrategy {

    public EmployeeSortXStrategy(CustomLinkedList<Employee> input, Consumer<CustomLinkedList<Employee>> callback) {
        super(input, callback);
    }

    @Override
    protected CustomLinkedList<Employee> performOperation() {
        System.out.println("\n=== СОРТИРОВОЧКА С НАСТРОЙКАМИ ===");
        
        EmployeeQuickSorter.SortingFields sf = SortingConfigurationPrompt.getSortingField();
        boolean acceding = SortingConfigurationPrompt.getAcceding();
        EmployeeQuickSorter.FilterFields ff = SortingConfigurationPrompt.getFilterField();
        Function<Long, Boolean> condition = SortingConfigurationPrompt.getConditionForField(ff.getKey());

        FilterConfiguration config = new FilterConfiguration(ff, condition);
        EmployeeQuickSorter sorter = new EmployeeQuickSorter(sf, acceding, config);

//        inputData.stream().filter(it -> it.getOrder() % 2 == 0).forEach(System.out::println);
        var list = sorter.sortEmployees(inputData);
//        System.out.println("---");
//        list.stream().filter(it -> it.getOrder() % 2 == 0).forEach(System.out::println);
        return list;
    }
}
