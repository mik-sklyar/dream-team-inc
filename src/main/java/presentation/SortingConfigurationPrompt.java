package presentation;

import business.sorting.EmployeeQuickSorter;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static business.sorting.EmployeeQuickSorter.FilterFields;
import static business.sorting.EmployeeQuickSorter.SortingFields;

public class SortingConfigurationPrompt {

    public static SortingFields getSortingField() throws ExitException {
        Scanner scanner = new Scanner(System.in);
        Map<String, SortingFields> map = IntStream.range(0, SortingFields.values().length).boxed()
                .collect(Collectors.toMap(i -> String.valueOf(i + 1), i -> SortingFields.values()[i]));

        while (true) {
            System.out.println("Выберите поле сортировки: ");
            map.forEach((index, value) -> System.out.println(index + " - " + value));
            System.out.print(">> ");
            String input = scanner.nextLine().strip();
            if (input.equals("0")) throw new ExitException();
            SortingFields result = map.get(input);
            if (result != null) return result;
            System.out.println("-- не правильный выбор, попробуем ещё раз...");
        }
    }

    public static FilterFields getFilterField() throws ExitException {
        Scanner scanner = new Scanner(System.in);
        Map<String, FilterFields> map = IntStream.range(0, FilterFields.values().length).boxed()
                .collect(Collectors.toMap(i -> String.valueOf(i + 1), i -> FilterFields.values()[i]));

        while (true) {
            System.out.println("Выберите поле по которому фильтровать: ");
            map.forEach((index, value) -> System.out.println(index + " - " + value));
            System.out.print(">> ");
            String input = scanner.nextLine().strip();
            if (input.equals("0")) throw new ExitException();
            FilterFields result = map.get(input);
            if (result != null) return result;
            System.out.println("-- не правильный выбор, попробуем ещё раз...");
        }
    }

    public static boolean getAcceding() throws ExitException {
        Scanner scanner = new Scanner(System.in);
        Map<String, Boolean> map = Map.of("1", true, "2", false);
        TreeMap<String, String> mapDesc = new TreeMap<>(Map.of("1", "по возрастанию", "2", "по убыванию"));

        while (true) {
            System.out.println("Выберите как сортировать: ");
            mapDesc.forEach((index, value) -> System.out.println(index + " - " + value));
            System.out.print(">> ");
            String input = scanner.nextLine().strip();
            if (input.equals("0")) throw new ExitException();
            Boolean result = map.get(input);
            if (result != null) return result;
            System.out.println("-- не правильный выбор, попробуем ещё раз...");
        }
    }

    public static Function<Long, Boolean> getConditionForField(String fieldName) throws ExitException {
        Scanner scanner = new Scanner(System.in);
        Map<String, Function<Long, Boolean>> map = Map.of(
                "1", EmployeeQuickSorter.FilterConfiguration::isLongEvenNumber,
                "2", EmployeeQuickSorter.FilterConfiguration::isLongNotEvenNumber
        );
        TreeMap<String, String> mapDesc = new TreeMap<>(Map.of(
                "1", "только с чётными значениями",
                "2", "только с нечётными значениями"
        ));

        while (true) {
            System.out.println("Выберите как сортировать по значению поля \"" + fieldName + "\": ");
            mapDesc.forEach((index, value) -> System.out.println(index + " - " + value));
            System.out.print(">> ");
            String input = scanner.nextLine().strip();
            if (input.equals("0")) throw new ExitException();
            Function<Long, Boolean> result = map.get(input);
            if (result != null) return result;
            System.out.println("-- не правильный выбор, попробуем ещё раз...");
        }
    }

    public static class ExitException extends Exception {
        public ExitException() {
            super();
        }

        public ExitException(String message) {
            super(message);
        }
    }

}
