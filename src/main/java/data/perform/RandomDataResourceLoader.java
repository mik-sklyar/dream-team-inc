package data.perform;

import data.CustomLinkedList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RandomDataResourceLoader {
    private final Path directory;
    private final CustomLinkedList<String> maleNames;
    private final CustomLinkedList<String> femaleNames;
    private final CustomLinkedList<String> domains;
    private final CustomLinkedList<String> works;

    public RandomDataResourceLoader(Path directory) {
        this.directory = directory;
        maleNames = loadFileToList("male-names-list.txt");
        femaleNames = loadFileToList("female-names-list.txt");
        domains = loadFileToList("domain-list.txt");
        works = loadFileToList("work-list.txt");
    }

    CustomLinkedList<String> getMaleNames() {
        return maleNames;
    }

    CustomLinkedList<String> getFemaleNames() {
        return femaleNames;
    }

    CustomLinkedList<String> getDomains() {
        return domains;
    }

    CustomLinkedList<String> getWorks() {
        return works;
    }

    private CustomLinkedList<String> loadFileToList(String fileName) {
        CustomLinkedList<String> list = null;

        try {
            list = (CustomLinkedList<String>) Files.readAllLines(
                    Paths.get(System.getProperty("user.dir"), directory.toString(), fileName).normalize()
            );
        } catch (IOException e) {
            System.err.println("Файл не найден: " + e);
        }

        return list;
    }
}
