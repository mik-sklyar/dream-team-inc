import business.ActionContext;
import business.sorting.EmployeeQuickSortStrategy;
import data.Employee;
import data.EmployeeSortingField;

import java.util.List;
import java.util.Scanner;

/**
 * Реализует меню для интерактивной работы со списком сотрудников.
 * <p>
 * Класс предоставляет пользователю возможность выбора действий
 * над полученным списком, включая его отображение и сортировку
 * по различным полям.
 */
public class DataActionsMenu {

    private final ActionContext context = new ActionContext();
    private List<Employee> employees;

    public DataActionsMenu(List<Employee> employees) {
        this.employees = employees;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Меню обработки данных ---");
            System.out.println("Получено сотрудников: " + employees.size());
            System.out.println("1 - Вывести список на экран");
            System.out.println("2 - Сортировать по имени");
            System.out.println("3 - Сортировать по email");
            System.out.println("4 - Сортировать по паролю");
            System.out.println("0 - Вернуться в главное меню");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    printEmployees();
                    break;
                case "2":
                    context.setStrategy(new EmployeeQuickSortStrategy(employees, EmployeeSortingField.NAME, this::handleSortResult));
                    break;
                case "3":
                    context.setStrategy(new EmployeeQuickSortStrategy(employees, EmployeeSortingField.EMAIL, this::handleSortResult));
                    break;
                case "4":
                    context.setStrategy(new EmployeeQuickSortStrategy(employees, EmployeeSortingField.PASSWORD, this::handleSortResult));
                    break;
                case "0":
                    System.out.println("Возврат в главное меню...");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
            context.perform();
        }
    }

    private void handleSortResult(List<Employee> sortedEmployees) {
        System.out.println("\n--- Обработано " + sortedEmployees.size() + " сотрудников ---");
        this.employees = sortedEmployees;
        printEmployees();
    }

    private void printEmployees() {
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }
        System.out.println("\n--- Текущий список сотрудников ---");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
}