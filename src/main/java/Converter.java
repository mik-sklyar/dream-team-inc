import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Converter {
    public static List<String> fileToList(Path directory, String fileName) {
        List<String> list = null;

        try {
            list = Files.readAllLines(
                    Paths.get(System.getProperty("user.dir"), directory.toString(), fileName).normalize()
            );
        } catch (IOException e) {
            System.err.println("Файл не найден: " + e);
        }

        return list;
    }

    public static List<String> passwordsToList(int minLength, int maxLength, int count) {
        List<String> passwords = new ArrayList<>(count);

        while (passwords.size() < count) {
            passwords.add(RandomDataEmployeesProvider.randomPassword(minLength, maxLength));
        }

        return passwords;
    }

    public static List<String> emailsToList(List<String> domains, List<String> works, int count) {
        List<String> emails = new ArrayList<>(count);

        while (emails.size() < count) {
            emails.add(RandomDataEmployeesProvider.randomEmail(domains, works));
        }

        return emails;
    }

    public static List<Employee> allDataToEmployeesList(List<String> male, List<String> female,
                                                        List<String> passwords, List<String> emails, int count) {
        try {

            Employee.Builder builder = new Employee.Builder();
            List<Employee> list = new ArrayList<>(count);

            Random random = new Random();

            int i, j;

            while (list.size() < count) {
                i = random.nextInt(2);
                if (i == 1) {
                    j = random.nextInt(male.size());

                    list.add(builder
                            .setName(male.get(j))
                            .setEmail(male.get(j) + emails.get(j))
                            .setPassword(passwords.get(j))
                            .build());
                } else {
                    j = random.nextInt(female.size());

                    list.add(builder
                            .setName(female.get(j))
                            .setEmail(female.get(j) + emails.get(j))
                            .setPassword(passwords.get(j))
                            .build());
                }
            }

            return list;
        } catch (IllegalArgumentException e) {
            System.err.print(e);
        }

        return null;
    }
}
