import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataPerformMenu {

    public enum DataPerform {

        FILE("1", "Устроить партию сотрудников из файла биржи труда"),
        MANUAL("2", "Заполнить сотрудников по очереди вручную"),
        RANDOM("3", "Получить сотрудников из параллельной вселенной \"Рандомии\""),
        EXIT("0", "Отказаться от амбиций и выйти"),
        UNKNOWN("", "");

        private static final Map<String, DataPerformMenu.DataPerform> MAP = new HashMap<>();

        static {
            for (DataPerformMenu.DataPerform value : DataPerformMenu.DataPerform.values()) {
                MAP.put(value.key, value);
            }
        }

        private final String key;
        private final String description;

        DataPerform(String key, String description) {
            this.key = key;
            this.description = description;

        }

        static public DataPerformMenu.DataPerform fromString(String input) {
            return Objects.requireNonNullElse(MAP.get(input), UNKNOWN);
        }

        @Override
        public String toString() {
            return key + " - " + description;
        }
    }
}
