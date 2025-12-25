package data.perform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class RandomDataResourceLoader {
    private final Path directory;
    private final List<String> maleNames;
    private final List<String> femaleNames;
    private final List<String> domains;
    private final List<String> works;

    public RandomDataResourceLoader(Path directory) {
        this.directory = directory;
        maleNames = loadFileToList("male-names-list.txt");
        femaleNames = loadFileToList("female-names-list.txt");
        domains = loadFileToList("domain-list.txt");
        works = loadFileToList("work-list.txt");
    }

    List<String> getMaleNames() {
        return maleNames;
    }

    List<String> getFemaleNames() {
        return femaleNames;
    }

    List<String> getDomains() {
        return domains;
    }

    List<String> getWorks() {
        return works;
    }

    private List<String> loadFileToList(String fileName) {
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
