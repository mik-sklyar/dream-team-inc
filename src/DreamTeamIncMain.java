
import java.util.Scanner;

import static java.lang.System.out;

public class DreamTeamIncMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        out.println("Добро пожаловать в Dream-Team inc.");
        out.println("Амбициозный стартап набирает сотрудников для захвата мира.");

        while (true) {
            out.println("1 - устроить партию сотрудников из файла биржи труда");
            out.println("2 - устроить сотрудников по очереди вручную");
            out.println("0 - отказаться от амбиций и выйти");
            out.print("Введите номер действия: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleFileInput();
                    break;
                case 2:
                    handleManualInput();
                    break;
                case 0:
                    out.println("Ну а что, тоже вариант ¯\\_(ツ)_/¯");
                    return;
                default:
                    out.println("Неверный выбор. Пожалуйста, введите 1, 2 или 0.");
            }
        }
    }

    private static void handleManualInput() {
        out.println("task 3 - пока не готово");
        // передать управление классу, который просит ввести необходимые данные, создаёт объекты и передаёт управление обратно с набором объектов
    }

    private static void handleFileInput() {
        out.println("task 2 - пока не готово");
        // передать управление классу, который просит ввести имя файла, передаёт управление парсеру, возвращает управление с набором данных
    }
}