import presentation.DataPerformMenu;
import presentation.Utils;

public class Main {
    private static final DataPerformMenu menu = new DataPerformMenu();

    public static void main(String[] args) {
        Utils.drawTree();

        System.out.println(Utils.veryLightBlueString("Добро пожаловать в Dream-Team inc."));
        System.out.println("Амбициозный стартап набирает сотрудников для захвата мира.");

        menu.display();
    }
}
