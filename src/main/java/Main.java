import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {

        out.println("Добро пожаловать в Dream-Team inc.");
        out.println("Амбициозный стартап набирает сотрудников для захвата мира.");

        ActionContext context = new ActionContext();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            out.println("1 - устроить партию сотрудников из файла биржи труда");
            out.println("2 - устроить сотрудников по очереди вручную");
            out.println("3 - взять сотрудников из параллельной вселенной \"Рандомии\"");
            out.println("0 - отказаться от амбиций и выйти");
            out.print("Введите номер действия: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    context.setStrategy(new FileDataPerformStrategy());
                    break;
                case "2":
                    context.setStrategy(new ManualDataPerformStrategy());
                    break;
                case "3":
                    context.setStrategy(new RandomDataPerformStrategy());
                    break;
                case "0":
                    out.println("Ну а что, тоже вариант ¯\\_(ツ)_/¯");
                    System.exit(0);
                default:
                    out.println("Неверный выбор, попробуйте снова.");
                    continue;
            }
            context.perform();
        }
    }

    public static void handleEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            out.println("А никто не пришёл... Попробуем ещё раз?");
        } else {
            out.println("Новых сотрудников " + employees.size());
            //[task6] передать управление классу с меню обработки данных
        }
    }
}

