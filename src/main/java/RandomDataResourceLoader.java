import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RandomDataResourceLoader {
    protected static List<String> loadFileToList(Path directory, String fileName) {
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
