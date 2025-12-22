import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataEmployeesProvider {

    protected static String provideRandomEmail(List<String> domains, List<String> works) {
        Random random = new Random();

        int w = random.nextInt(works.size());
        int d = random.nextInt(domains.size());

        return new StringBuilder()
                .append(works.get(w))
                .append("@")
                .append(domains.get(d))
                .toString();
    }

    protected static String provideRandomPassword(int minLength, int maxLength) {
        try {
            if (minLength < 6 || minLength > maxLength) {
                throw new IllegalArgumentException("Ошибка в соответствии входящих значений");
            }

            StringBuilder builder = new StringBuilder();

            char[] symbols = passwordSymbols();

            maxLength = (int) (Math.random() * (maxLength - minLength) + minLength);

            while (builder.length() < maxLength) {
                builder.append(symbols[(int) (Math.random() * symbols.length - 1)]);
            }

            return builder.toString();
        } catch (IllegalArgumentException e) {
            System.err.println(e);
        }

        return null;
    }

    protected static List<Employee> provideRandomEmployees(int employeesCount) {
        try {
            if (employeesCount == 0) {
                return new ArrayList<>();
            }

            Path directory = Path.of("src", "main", "resources");

            List<String> maleNames = RandomDataResourceLoader.loadFileToList(directory, "male-names-list.txt");
            List<String> femaleNames = RandomDataResourceLoader.loadFileToList(directory, "female-names-list.txt");
            List<String> domains = RandomDataResourceLoader.loadFileToList(directory, "domain-list.txt");
            List<String> works = RandomDataResourceLoader.loadFileToList(directory, "work-list.txt");

            int passwordCount = maleNames.size() + femaleNames.size();
            int emailsCount = maleNames.size() + femaleNames.size();

            List<String> passwords = RandomDataResourceLoader.loadPasswordsToList(6, 20, passwordCount);
            List<String> emails = RandomDataResourceLoader.loadEmailsToList(domains, works, emailsCount);

            return RandomDataResourceLoader.loadAllDataToEmployeesList(maleNames, femaleNames, passwords, emails, employeesCount);
        } catch (IllegalArgumentException e) {
            System.err.println(e);
        } catch (NullPointerException e) {
            System.err.println("Пустой List, причина файл не найден");
        }
        return null;
    }

    private static char[] passwordSymbols() {
        char[] charArr = new char[93];

        int min = 33, max = 126;

        for (int i = 0, j = 92; i <= j; i++, j--) {
            charArr[i] = (char) min++;
            charArr[j] = (char) max--;
        }

        return charArr;
    }
}
