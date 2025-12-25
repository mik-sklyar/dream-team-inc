package business;

import data.Employee;

import java.util.List;

public class EmployeePrintStrategy extends EmployeeOperationStrategy {
    public EmployeePrintStrategy(List<Employee> input) {
        super(input, null);
    }

    @Override
    protected List<Employee> performOperation() {
        if (inputData.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
        } else {
            System.out.println("\n--- Текущий список сотрудников ---");
            for (Employee employee : inputData) {
                System.out.println(employee);
            }
        }
        return inputData;
    }
}
