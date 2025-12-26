package presentation;

import static presentation.Utils.INPUT;

public class EmployeeNumberPrompt {
    private final String promptMessage;

    public EmployeeNumberPrompt(String promptMessage) {
        this.promptMessage = promptMessage;

    }

    public int getCount() {
        while (true) {
            System.out.print(promptMessage);
            String input = INPUT.nextLine().trim();  // ← Здесь!

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
