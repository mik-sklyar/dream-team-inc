import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class Generator {

    public static String randomEmail(List<String> domains, List<String> works) {
        Random random = new Random();

        int w = random.nextInt(works.size());
        int d = random.nextInt(domains.size());

        return new StringBuilder()
                .append(works.get(w))
                .append("@")
                .append(domains.get(d))
                .toString();
    }

    public static String randomPassword(int minLength, int maxLength) {
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

    public static List<Employee> randomEmployees(int employeesCount) {
        try {
            if (employeesCount < 1) {throw new IllegalArgumentException("Число сотрудников меньше 1");}

            Path directory = Path.of("src", "main", "resources");

            List<String> maleNames = Converter.fileToList(directory, "male-names-list.txt");
            List<String> femaleNames = Converter.fileToList(directory, "female-names-list.txt");
            List<String> domains = Converter.fileToList(directory, "domain-list.txt");
            List<String> works = Converter.fileToList(directory, "work-list.txt");

            int passwordCount = maleNames.size() + femaleNames.size();
            int emailsCount = maleNames.size() + femaleNames.size();

            List<String> passwords = Converter.passwordsToList(6, 20, passwordCount);
            List<String> emails = Converter.emailsToList(domains, works, emailsCount);

            return Converter.allDataToEmployeesList(maleNames, femaleNames, passwords, emails, employeesCount);
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
