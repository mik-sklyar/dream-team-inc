package presentation;

import business.ActionContext;
import business.ExitStrategy;
import business.perform.FileDataPerformStrategy;
import business.perform.ManualDataPerformStrategy;
import business.perform.RandomDataPerformStrategy;
import data.CustomLinkedList;
import data.Employee;

import java.util.*;

public class DataPerformMenu {

    public void display() {
        Scanner scanner = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("\n--- Меню получения данных ---");
            for (DataPerformMenuItems value : DataPerformMenuItems.values()) {
                if (value == DataPerformMenuItems.UNKNOWN) continue;
                System.out.println(value);
            }
            System.out.print("Выберите действие: ");

            ActionContext context = new ActionContext();
            DataPerformMenuItems choice = DataPerformMenuItems.fromString(scanner.nextLine().strip());
            switch (choice) {
                case FILE:
                    context.setStrategy(new FileDataPerformStrategy(this::handleEmployees));
                    break;
                case MANUAL:
                    context.setStrategy(new ManualDataPerformStrategy(this::handleEmployees));
                    break;
                case RANDOM:
                    context.setStrategy(new RandomDataPerformStrategy(this::handleEmployees));
                    break;
                case EXIT:
                    context.setStrategy(new ExitStrategy());
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
                    continue;
            }
            context.perform();
        }
    }

    private void handleEmployees(CustomLinkedList<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("А никто не пришёл... Попробуем ещё раз?");
        } else {
            DataActionsMenu menu = new DataActionsMenu(employees);
            menu.display();
        }
    }

    private enum DataPerformMenuItems {

        FILE("1", "Устроить партию сотрудников из файла биржи труда"),
        MANUAL("2", "Заполнить сотрудников по очереди вручную"),
        RANDOM("3", "Получить сотрудников из параллельной вселенной \"Рандомии\""),
        EXIT("0", "Отказаться от амбиций и выйти"),
        UNKNOWN("", "");

        private static final Map<String, DataPerformMenuItems> MAP = new HashMap<>();

        static {
            for (DataPerformMenuItems value : DataPerformMenuItems.values()) {
                MAP.put(value.key, value);
            }
        }

        private final String key;
        private final String description;

        DataPerformMenuItems(String key, String description) {
            this.key = key;
            this.description = description;

        }

        static private DataPerformMenuItems fromString(String input) {
            return Objects.requireNonNullElse(MAP.get(input), UNKNOWN);
        }

        @Override
        public String toString() {
            return key + " - " + description;
        }
    }
}
