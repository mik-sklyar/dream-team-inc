package business;

public class ExitStrategy implements ActionStrategy {
    @Override
    public void execute() {
        System.out.println("\nНу а что, тоже вариант ¯\\_(ツ)_/¯");
        System.exit(0);
    }
}
