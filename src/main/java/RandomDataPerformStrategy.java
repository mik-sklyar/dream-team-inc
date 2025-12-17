import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RandomDataPerformStrategy implements ActionStrategy {
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public void execute() {
        System.out.println("[task 4] Under construction");
        try {
            List<String> maleNames = writeFileToList("male-names-list.txt");
            List<String> femaleNames = writeFileToList("female-names-list.txt");
            List<String> domains = writeFileToList("domain-list.txt");
            List<String> works = writeFileToList("work-list.txt");

            int count = maleNames.size() + femaleNames.size();

            List<String> passwords = generatePasswordsToList(6, 20, count);
            List<String> emails = generateEmailsToList(domains, works, count);
            employees = generateEmployees(maleNames, femaleNames, passwords, emails);
        } catch (NullPointerException e) {
            System.err.println("Пустой List, причина файл не найден");
        }
    }

    private List<String> writeFileToList(String fileName) {
        try {
            return Files.readAllLines(Paths.get(
                            System.getProperty("user.dir")
                                    + "\\src\\main\\resources\\"
                                    + fileName
                    ).normalize()
            );
        } catch (IOException e) {
            System.err.println("Файл " + fileName + " не найден: " + e);
        }

        return null;
    }

    private List<String> generatePasswordsToList(int minLength, int maxLength, int count) {
        List<String> passwords = new ArrayList<>(count);

        while (passwords.size() < count) {
            passwords.add(Generator.randomPassword(minLength, maxLength));
        }

        return passwords;
    }

    private List<String> generateEmailsToList(List<String> domains, List<String> works, int count) {
        List<String> emails = new ArrayList<>(count);

        while (emails.size() < count) {
            emails.add(Generator.randomEmail(domains, works));
            emails.add(Generator.randomEmail(domains, works));
        }

        return emails;
    }

    private List<Employee> generateEmployees(List<String> male, List<String> female,
                                             List<String> passwords, List<String> emails) {
        Employee.Builder builder = new Employee.Builder();
        List<Employee> list = new ArrayList<>(male.size() + female.size());
        List<String> min = female.size() > male.size() ? male : female;
        List<String> max = female.size() > male.size() ? female : male;

        int i = 0;
        int count = min.size() + min.size() / 2;

        for (; list.size() < count; i++) {
            list.add(builder
                    .setName(min.get(i))
                    .setEmail(min.get(i) + emails.get(i))
                    .setPassword(passwords.get(i))
                    .build());
            list.add(builder
                    .setName(max.get(i))
                    .setEmail(max.get(i) + emails.get(i))
                    .setPassword(passwords.get(i))
                    .build());
        }

        i /= 2;
        count = max.size()+ min.size();

        for (; list.size() < count; i++) {
            list.add(builder
                    .setName(max.get(i))
                    .setEmail(max.get(i) + emails.get(i))
                    .setPassword(passwords.get(i))
                    .build());
        }

        return list;
    }

    static class Generator {
        static String randomEmail(List<String> domains, List<String> works) {
            Random random = new Random();

            int w = random.nextInt(works.size());
            int d = random.nextInt(domains.size());

            return new StringBuilder()
                    .append(works.get(w))
                    .append("@")
                    .append(domains.get(d))
                    .toString();
        }

        static String randomPassword(int minLength, int maxLength) {
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

        static char[] passwordSymbols() {
            char[] charArr = new char[93];

            int min = 33, max = 126;

            for (int i = 0, j = 92; i <= j; i++, j--) {
                charArr[i] = (char) min++;
                charArr[j] = (char) max--;
            }

            return charArr;
        }

    }
}
