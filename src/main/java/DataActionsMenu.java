import business.ActionContext;
import business.EmployeePrintStrategy;
import business.ExitStrategy;
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
            System.out.println("К работе готовы " + employees.size() + " сотрудников.");
            System.out.println("1 - Вывести список на экран");
            System.out.println("2 - Сортировать по порядку");
            System.out.println("3 - Сортировать по id");
            System.out.println("4 - Сортировать по имени");
            System.out.println("5 - Сортировать по email");
            System.out.println("9 - Эти не годятся, начать заново набирать команду");
            System.out.println("0 - Отказаться от всего этого и уйти");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    context.setStrategy(new EmployeePrintStrategy(employees));
                    break;
                case "2":
                    context.setStrategy(new EmployeeQuickSortStrategy(employees, EmployeeSortingField.ORDER, this::handleSortResult));
                    break;
                case "3":
                    context.setStrategy(new EmployeeQuickSortStrategy(employees, EmployeeSortingField.ID, this::handleSortResult));
                    break;
                case "4":
                    context.setStrategy(new EmployeeQuickSortStrategy(employees, EmployeeSortingField.NAME, this::handleSortResult));
                    break;
                case "5":
                    context.setStrategy(new EmployeeQuickSortStrategy(employees, EmployeeSortingField.EMAIL, this::handleSortResult));
                    break;
                case "0":
                    context.setStrategy(new ExitStrategy());
                    break;
                case "9":
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