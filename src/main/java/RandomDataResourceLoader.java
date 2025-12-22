import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

final class RandomDataResourceLoader {
    private final static Path directory = Path.of("src", "main", "resources");

    private final static List<String> maleNames;
    private final static List<String> femaleNames;
    private final static List<String> domains;
    private final static List<String> works;

    static {
        maleNames = RandomDataResourceLoader.loadFileToList("male-names-list.txt");
        femaleNames = RandomDataResourceLoader.loadFileToList("female-names-list.txt");
        domains = RandomDataResourceLoader.loadFileToList("domain-list.txt");
        works = RandomDataResourceLoader.loadFileToList("work-list.txt");
    }

    static List<String> getMaleNames() {
        return maleNames;
    }

    static List<String> getFemaleNames() {
        return femaleNames;
    }

    static List<String> getDomains() {
        return domains;
    }

    static List<String> getWorks() {
        return works;
    }

    private static List<String> loadFileToList(String fileName) {
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
}
