import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFileReader {
    
    public List<Employee> readEmployeesFromFile(File file) throws IOException {
        List<Employee> employees = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            int successfulCount = 0;
            int errorCount = 0;
            
            System.out.println("Начинаем чтение файла: " + file.getAbsolutePath());
            System.out.println("--------------------------------------------------");
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                
                
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                String[] parts = line.split("[;,\\t]");
                
                if (parts.length != 3) {
                    System.out.println("Ошибка в строке " + lineNumber + 
                                     ": неверный формат. Ожидается: Имя;email;пароль");
                    System.out.println("   Содержимое строки: " + line);
                    errorCount++;
                    continue;
                }
                
                try {
                    Employee employee = new Employee.Builder()
                            .setName(parts[0].trim())
                            .setEmail(parts[1].trim())
                            .setPassword(parts[2].trim())
                            .build();
                    employees.add(employee);
                    successfulCount++;
                    
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                    System.out.println("   Содержимое строки: " + line);
                    errorCount++;
                }
            }
            
            
            System.out.println("--------------------------------------------------");
            System.out.println("СТАТИСТИКА ЧТЕНИЯ ФАЙЛА:");
            System.out.println("   Успешно загружено: " + successfulCount + " сотрудников");
            System.out.println("   Ошибок при чтении: " + errorCount + " строк");
            System.out.println("   Всего обработано строк: " + lineNumber);
            System.out.println("--------------------------------------------------");
            
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            throw e;
        }
        
        return employees;
    }
    
    public File findFile(String filename) {
        
        File file = new File(filename);
        if (file.exists() && file.isFile()) {
            System.out.println("Файл найден по указанному пути: " + file.getAbsolutePath());
            return file;
        }
        
        
        System.out.println("Поиск файла в ресурсах проекта...");
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        
        if (resourceUrl != null) {
            try {
                file = new File(resourceUrl.toURI());
                System.out.println("Файл найден в ресурсах: " + file.getAbsolutePath());
                return file;
            } catch (Exception e) {
                System.out.println("Ошибка при доступе к ресурсу: " + e.getMessage());
            }
        }
        
        String[] possiblePaths = {
            "src/main/resources/" + filename,  // Maven/Gradle структура
            "src/resources/" + filename,       // Простая структура
            "resources/" + filename,           // Папка в корне
            filename                           // В корне проекта
        };
        
        for (String path : possiblePaths) {
            file = new File(path);
            if (file.exists() && file.isFile()) {
                System.out.println("Файл найден: " + file.getAbsolutePath());
                return file;
            }
        }
        
        System.out.println("Файл не найден в проекте: " + filename);
        return null;
    }
}
