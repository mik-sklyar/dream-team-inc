import java.util.Collections;
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

    private final List<Employee> employees;

    /**
     * Конструктор меню действий.
     *
     * @param employees Список сотрудников для обработки.
     */
    public DataActionsMenu(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Отображает меню и обрабатывает выбор пользователя.
     */
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
                    sortByName();
                    break;
                case "3":
                    sortByEmail();
                    break;
                case "4":
                    sortByPassword();
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
     * Выводит список сотрудников на экран.
     */
    private void printEmployees() {
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }
        System.out.println("\n--- Список сотрудников ---");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    /**
     * Заглушка для сортировки списка сотрудников по имени.
     */
    private void sortByName() {
        System.out.println("Сортировка по имени (в разработке)...");
        // TODO: Реализовать алгоритм сортировки без использования готовых методов.
        printEmployees();
    }

    /**
     * Заглушка для сортировки списка сотрудников по email.
     */
    private void sortByEmail() {
        System.out.println("Сортировка по email (в разработке)...");
        // TODO: Реализовать алгоритм сортировки без использования готовых методов.
        // Пример использования компаратора:
        // manualSort(employees, Comparator.comparing(Employee::getEmail));
        printEmployees();
    }

    /**
     * Заглушка для сортировки списка сотрудников по паролю.
     */
    private void sortByPassword() {
        System.out.println("Сортировка по паролю (в разработке)...");
        // TODO: Реализовать алгоритм сортировки без использования готовых методов.
        printEmployees();
    }

    /**
     * Метод для ручной реализации алгоритма сортировки (например, пузырьком или вставками).
     *
     * @param list       Список для сортировки.
     * @param comparator Компаратор для сравнения элементов.
     * @param <T>        Тип элементов в списке.
     */
    private <T> void manualSort(List<T> list, Comparator<T> comparator) {
        // TODO: Реализовать собственный алгоритм сортировки.
        System.out.println("Здесь будет реализован собственный алгоритм сортировки.");
    }
}