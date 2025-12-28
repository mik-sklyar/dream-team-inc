package business;

import data.CustomLinkedList;
import data.Employee;

public class EmployeePrintStrategy extends EmployeeOperationStrategy {
    public EmployeePrintStrategy(CustomLinkedList<Employee> input) {
        super(input, null);
    }

    @Override
    protected CustomLinkedList<Employee> performOperation() {
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
