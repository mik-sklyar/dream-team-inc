import data.CustomLinkedList;
import data.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataEmployeesProvider {

    private static List<String> provideRandomEmails(List<String> domains, List<String> works, int count) {
        Random random = new Random();

        int w, d;

        List<String> emails = new CustomLinkedList<>();

        while (emails.size() < count) {
            w = random.nextInt(works.size());
            d = random.nextInt(domains.size());

            emails.add(works.get(w) + "@" + domains.get(d));
        }

        return emails;
    }

    private static List<String> provideRandomPasswords(int minLength, int maxLength, int count) {
        try {
            if (minLength < 6 || minLength > maxLength) {
                throw new IllegalArgumentException("Ошибка в соответствии входящих значений");
            }

            List<String> passwords = new CustomLinkedList<>();
            StringBuilder builder = new StringBuilder();

            char[] symbols = passwordSymbols();

            while (passwords.size() < count) {
                while (builder.length() < maxLength) {
                    maxLength = (int) (Math.random() * (maxLength - minLength) + minLength);
                    builder.append(symbols[(int) (Math.random() * symbols.length - 1)]);
                }

                passwords.add(builder.toString());
                builder.setLength(0);
            }

            return passwords;
        } catch (IllegalArgumentException e) {
            System.err.println(e);
        }

        return null;
    }

    static List<Employee> provideRandomEmployees(List<String> maleNames, List<String> femaleNames,
                                                 List<String> domains, List<String> works,
                                                 int employeesCount) {
        try {
            if (employeesCount == 0) {
                return new ArrayList<>();
            }

            int passwordCount = maleNames.size() + femaleNames.size();
            int emailsCount = maleNames.size() + femaleNames.size();

            List<String> passwords = RandomDataEmployeesProvider.provideRandomPasswords(6, 20, passwordCount);
            List<String> emails = RandomDataEmployeesProvider.provideRandomEmails(domains, works, emailsCount);

            Employee.Builder builder = new Employee.Builder();
            List<Employee> list = new ArrayList<>(employeesCount);

            Random random = new Random();

            int i, j;

            while (list.size() < employeesCount) {
                i = random.nextInt(2);
                if (i == 1) {
                    j = random.nextInt(maleNames.size());

                    list.add(builder
                            .setName(maleNames.get(j))
                            .setEmail(maleNames.get(j) + emails.get(j))
                            .setPassword(passwords.get(j))
                            .build());
                } else {
                    j = random.nextInt(femaleNames.size());

                    list.add(builder
                            .setName(femaleNames.get(j))
                            .setEmail(femaleNames.get(j) + emails.get(j))
                            .setPassword(passwords.get(j))
                            .build());
                }
            }

            return list;

        } catch (IllegalArgumentException | NullPointerException e) {
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
