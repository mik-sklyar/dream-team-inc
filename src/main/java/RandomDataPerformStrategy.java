import java.util.*;


public class RandomDataPerformStrategy implements ActionStrategy {
    List<Employee> employees;

    @Override
    public void execute() {
        int count;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.print("Введите количество сотрудников для призыва(больше 0):");
                count = Integer.parseInt(scanner.nextLine());
                if (count > 0) break;
                else throw new InputMismatchException();
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Неверный тип данных или число сотрудников, попробуйте еще раз");
            }
        } while (true);

        scanner.close();

        System.out.println("Богиня жизни \"Гея\" призвала новых сотрудников");
        employees = RandomDataEmployeesProvider.provideRandomEmployees(count);
    }

    public static void main(String[] args) {
        RandomDataPerformStrategy strategy = new RandomDataPerformStrategy();
        strategy.execute();
        System.out.println(strategy.employees);
    }
}
