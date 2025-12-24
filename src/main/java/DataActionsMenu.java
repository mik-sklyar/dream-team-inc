import business.ActionContext;
import data.Employee;

import java.util.Comparator;
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

    private List<Employee> employees;
    private final ActionContext context = new ActionContext();

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
                    setSortStrategy(Comparator.comparing(Employee::getName));
                    break;
                case "3":
                    setSortStrategy(Comparator.comparing(Employee::getEmail));
                    break;
                case "4":
                    setSortStrategy(Comparator.comparing(Employee::getPassword));
                    break;
                case "0":
                    System.out.println("Возврат в главное меню...");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    /**
     * Устанавливает и выполняет стратегию сортировки.
     *
     * @param comparator Компаратор, определяющий поле для сортировки.
     */
    private void setSortStrategy(Comparator<Employee> comparator) {
        // TODO: Устанавливаем стратегию сортировки
        System.out.println("Выполняется сортировка...");
        context.perform();
        System.out.println("Сортировка завершена.");
        printEmployees();
    }

    private void handleSortResult(List<Employee> sortedEmployees) {
        this.employees = sortedEmployees;
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