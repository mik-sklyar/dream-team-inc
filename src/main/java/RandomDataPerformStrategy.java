import com.sun.jdi.IncompatibleThreadStateException;

import java.io.InvalidClassException;
import java.util.*;


public class RandomDataPerformStrategy implements ActionStrategy {
    List<Employee> employees;
    @Override
    public void execute() {
        int count;
        Scanner scanner = new Scanner(System.in);

        do {
            try {

                System.out.print("Введите количество сотрудников для призыва:");
                count = Integer.parseInt(scanner.nextLine());

                if (count > 0) break;
                else throw new InputMismatchException();
            } catch (NumberFormatException e) {
                System.out.println("Неверный тип данных, попробуйте еще раз");
            } catch (InputMismatchException e) {
                System.out.println("Неверное число, попробуйте еще раз");
            }
        } while (true);

        scanner.close();

        System.out.println("Богиня жизни \"Гея\" призвала новых сотрудников");
        employees = Generator.randomEmployees(count);
    }
}
