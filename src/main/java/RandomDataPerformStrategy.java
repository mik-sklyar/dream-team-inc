import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;


public class RandomDataPerformStrategy extends EmployeeOperationStrategy {
    public RandomDataPerformStrategy() {
        super();
    }

    public RandomDataPerformStrategy(Consumer<List<Employee>> callback) {
        super(callback);
    }

    @Override
    protected List<Employee> performOperation() {
        int count;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.print("Введите количество сотрудников для призыва(больше 0):");
                count = Integer.parseInt(scanner.nextLine());
                if (count > 0){
                    scanner.close();
                    break;
                }
                else {
                    throw new InputMismatchException();
                }
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Неверный тип данных или число сотрудников, попробуйте еще раз");
            }
        } while (true);

        System.out.println("Богиня жизни \"Гея\" призвала новых сотрудников");

        return RandomDataEmployeesProvider.provideRandomEmployees(
                RandomDataResourceLoader.getMaleNames(),
                RandomDataResourceLoader.getFemaleNames(),
                RandomDataResourceLoader.getDomains(),
                RandomDataResourceLoader.getWorks(),
                count
        );
    }
}
