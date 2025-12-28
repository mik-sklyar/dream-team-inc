package presentation;

import java.util.Scanner;

public class EmployeeNumberPrompt {
    private final String promptMessage;
    private final Scanner scanner;

    public EmployeeNumberPrompt(String promptMessage) {
        this.promptMessage = promptMessage;
        this.scanner = new Scanner(System.in);
    }

    public int getCount() {
        while (true) {
            System.out.print(promptMessage);
            String input = scanner.nextLine().trim();

            try {
                int count = Integer.parseInt(input);

                if (count >= 0) {
                    return count;
                } else {
                    System.out.println("Ошибка: число должно быть неотрицательным");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число (например: 5 или 0)");
            }
        }
    }
}
