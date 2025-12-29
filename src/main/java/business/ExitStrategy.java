package business;

import presentation.Utils;

public class ExitStrategy implements ActionStrategy {
    @Override
    public void execute() {
        System.out.println(Utils.veryLightBlueString("\nНу а что, тоже вариант ¯\\_(ツ)_/¯"));
        System.exit(0);
    }
}
