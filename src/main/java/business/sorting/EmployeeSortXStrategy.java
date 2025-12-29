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
        try {
            EmployeeQuickSorter.SortingFields sf = SortingConfigurationPrompt.getSortingField();
            boolean acceding = SortingConfigurationPrompt.getAcceding();
            EmployeeQuickSorter.FilterFields ff = SortingConfigurationPrompt.getFilterField();
            Function<Long, Boolean> condition = SortingConfigurationPrompt.getConditionForField(ff.getKey());

            FilterConfiguration config = new FilterConfiguration(ff, condition);
            EmployeeQuickSorter sorter = new EmployeeQuickSorter(sf, acceding, config);

            return sorter.sortEmployees(inputData);
        } catch (SortingConfigurationPrompt.ExitException e) {
            return null;
        }
    }
}
