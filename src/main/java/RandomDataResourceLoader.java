import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

final class RandomDataResourceLoader {
    private Path directory;
    private List<String> maleNames;
    private List<String> femaleNames;
    private List<String> domains;
    private List<String> works;

    RandomDataResourceLoader(Path directory) {
        this.directory = directory;
        maleNames = loadFileToList("male-names-list.txt");
        femaleNames = loadFileToList("female-names-list.txt");
        domains = loadFileToList("domain-list.txt");
        works = loadFileToList("work-list.txt");
    }

    RandomDataResourceLoader(Path directory, String maleNames, String femaleNames, String domains, String works) {
        this.directory = directory;
        this.maleNames = loadFileToList(maleNames);
        this.femaleNames = loadFileToList(femaleNames);
        this.domains = loadFileToList(domains);
        this.works = loadFileToList(works);
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
