import java.nio.file.Path;
import java.util.*;


public class RandomDataPerformStrategy implements ActionStrategy {
    @Override
    public List<Employee> execute() {
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

        Path directory = Path.of("src", "main", "resources");

        List<String> maleNames = RandomDataResourceLoader.loadFileToList(directory, "male-names-list.txt");
        List<String> femaleNames = RandomDataResourceLoader.loadFileToList(directory, "female-names-list.txt");
        List<String> domains = RandomDataResourceLoader.loadFileToList(directory, "domain-list.txt");
        List<String> works = RandomDataResourceLoader.loadFileToList(directory, "work-list.txt");

        System.out.println("Богиня жизни \"Гея\" призвала новых сотрудников");

        return RandomDataEmployeesProvider.provideRandomEmployees(maleNames, femaleNames, domains, works, count);
    }
}
