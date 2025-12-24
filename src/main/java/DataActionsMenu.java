import business.ActionContext;
import business.EmployeePrintStrategy;
import business.ExitStrategy;
import business.sorting.EmployeeSortStrategy;
import data.Employee;
import data.Employee.SortingFields;

import java.util.*;

/**
 * Реализует меню для интерактивной работы со списком сотрудников.
 * <p>
 * Класс предоставляет пользователю возможность выбора действий
 * над полученным списком, включая его отображение и сортировку
 * по различным полям.
 */
public class DataActionsMenu {

    private List<Employee> employees;

    public DataActionsMenu(List<Employee> employees) {
        this.employees = employees;
    }

    public void display() {
        ActionContext context = new ActionContext();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Меню обработки данных ---");
            System.out.println("К работе готовы " + employees.size() + " сотрудников.");
            for (DataAction action : DataAction.values()) {
                if (action == DataAction.UNKNOWN) continue;
                System.out.println(action);
            }
            System.out.print("Выберите действие: ");

            DataAction choice = DataAction.fromString(scanner.nextLine().strip());

            switch (choice) {
                case PRINT:
                    context.setStrategy(new EmployeePrintStrategy(employees));
                    break;
                case SORT_BY_ORDER:
                    context.setStrategy(new EmployeeSortStrategy(employees, SortingFields.ORDER, this::handleSortResult));
                    break;
                case SORT_BY_ID:
                    context.setStrategy(new EmployeeSortStrategy(employees, SortingFields.ID, this::handleSortResult));
                    break;
                case SORT_BY_NAME:
                    context.setStrategy(new EmployeeSortStrategy(employees, SortingFields.NAME, this::handleSortResult));
                    break;
                case SORT_BY_EMAIL:
                    context.setStrategy(new EmployeeSortStrategy(employees, SortingFields.EMAIL, this::handleSortResult));
                    break;
                case EXIT:
                    context.setStrategy(new ExitStrategy());
                    break;
                case RETURN:
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
                    continue;
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

    public enum DataAction {

        PRINT("1", "Вывести список на экран"),
        SORT_BY_ORDER("2", "Сортировать по порядку"),
        SORT_BY_ID("3", "Сортировать по id"),
        SORT_BY_NAME("4", "Сортировать по имени"),
        SORT_BY_EMAIL("5", "Сортировать по email"),
        RETURN("9", "Эти не годятся, начать заново набирать команду"),
        EXIT("0", "Отказаться от всего этого и уйти"),
        UNKNOWN("", "");

        private static final Map<String, DataAction> MAP = new HashMap<>();

        static {
            for (DataAction action : DataAction.values()) {
                MAP.put(action.key, action);
            }
        }

        private final String key;
        private final String description;

        DataAction(String key, String description) {
            this.key = key;
            this.description = description;

        }

        static public DataAction fromString(String input) {
            return Objects.requireNonNullElse(MAP.get(input), UNKNOWN);
        }

        @Override
        public String toString() {
            return key + " - " + description;
        }
    }
}