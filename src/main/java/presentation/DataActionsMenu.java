package presentation;

import business.ActionContext;
import business.EmployeePrintStrategy;
import business.ExitStrategy;
import business.perform.FindDuplicatePasswordsStrategy;
import business.sorting.EmployeeSortStrategy;
import business.sorting.EmployeeSortXStrategy;
import data.CustomLinkedList;
import data.Employee;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static business.sorting.EmployeeQuickSorter.SortingFields;

/**
 * Реализует меню для интерактивной работы со списком сотрудников.
 * <p>
 * Класс предоставляет пользователю возможность выбора действий
 * над полученным списком, включая его отображение и сортировку
 * по различным полям.
 */
public class DataActionsMenu {

    private CustomLinkedList<Employee> employees;

    public DataActionsMenu(CustomLinkedList<Employee> employees) {
        this.employees = employees;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== МЕНЮ ОБРАБОТКИ ДАННЫХ ===");
            System.out.println("К работе готовы " + employees.size() + " сотрудников.");
            for (DataActionMenuItems action : DataActionMenuItems.values()) {
                if (action == DataActionMenuItems.UNKNOWN) continue;
                System.out.println(action);
            }
            System.out.print("Выберите действие: ");

            ActionContext context = new ActionContext();
            DataActionMenuItems choice = DataActionMenuItems.fromString(scanner.nextLine().strip());
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
                case SORT_SPECIAL:
                    context.setStrategy(new EmployeeSortXStrategy(employees, this::handleSortResult));
                case FIND_DUPLICATE_PASSWORDS:
                    context.setStrategy(new FindDuplicatePasswordsStrategy(employees));
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

    private void handleSortResult(CustomLinkedList<Employee> sortedEmployees) {
        if (sortedEmployees == null) {
            return;
        }
        System.out.println("\n--- Обработано " + sortedEmployees.size() + " сотрудников ---");
        this.employees = sortedEmployees;
    }

    private enum DataActionMenuItems {

        PRINT("1", "Вывести список на экран"),
        SORT_BY_ORDER("2", "Сортировать по порядку"),
        SORT_BY_ID("3", "Сортировать по id"),
        SORT_BY_NAME("4", "Сортировать по имени"),
        SORT_BY_EMAIL("5", "Сортировать по email"),
        SORT_SPECIAL("6", "Сортировать c переподвыподвертом"),
        FIND_DUPLICATE_PASSWORDS("7", "Найти одинаковые пароли"),
        RETURN("0", "Эти не годятся, начать заново набирать команду"),
        EXIT("Q", "Отказаться от всего этого и уйти"),
		UNKNOWN("", "");

        private static final Map<String, DataActionMenuItems> MAP = new HashMap<>();

        static {
            for (DataActionMenuItems action : DataActionMenuItems.values()) {
                MAP.put(action.key.toLowerCase(), action);
            }
        }

        private final String key;
        private final String description;

        DataActionMenuItems(String key, String description) {
            this.key = key;
            this.description = description;
        }

        static private DataActionMenuItems fromString(String input) {
            return Objects.requireNonNullElse(MAP.get(input.toLowerCase()), UNKNOWN);
        }

        @Override
        public String toString() {
            return key + " - " + description;
        }
    }
}
