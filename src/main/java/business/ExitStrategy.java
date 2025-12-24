package business;

import static java.lang.System.out;

public class ExitStrategy implements ActionStrategy {
    @Override
    public void execute() {
        out.println("\nНу а что, тоже вариант ¯\\_(ツ)_/¯");
        System.exit(0);
    }
}
