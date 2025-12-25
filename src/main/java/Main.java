import static java.lang.System.out;

public class Main {
    private static final DataPerformMenu menu = new DataPerformMenu();

    public static void main(String[] args) {

        out.println("Добро пожаловать в Dream-Team inc.");
        out.println("Амбициозный стартап набирает сотрудников для захвата мира.");

        menu.display();
    }
}
