import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class FileDataPerformStrategy extends EmployeeOperationStrategy {
    
    private final EmployeeFileReader fileReader;
    private final Scanner scanner;
    
    // Конструктор для ручного ввода (с callback)
    public FileDataPerformStrategy(Consumer<List<Employee>> callback) {
        super(callback);
        this.fileReader = new EmployeeFileReader();
        this.scanner = new Scanner(System.in);
    }
    
    // Конструктор для автоматического тестирования (с входными данными и callback)
    public FileDataPerformStrategy(List<Employee> inputData, Consumer<List<Employee>> callback) {
        super(inputData, callback);
        this.fileReader = new EmployeeFileReader();
        this.scanner = new Scanner(System.in);
    }
    
    // Конструктор по умолчанию (просто выводит результат)
    public FileDataPerformStrategy() {
        super();
        this.fileReader = new EmployeeFileReader();
        this.scanner = new Scanner(System.in);
    }

    @Override
    protected List<Employee> performOperation() {
        System.out.println("\n=== ЗАГРУЗКА СОТРУДНИКОВ ИЗ ФАЙЛА ===");
        
        while (true) {
            System.out.print("Введите имя файла: ");
            String filename = scanner.nextLine().trim();
            
            if (filename.isEmpty()) {
                System.out.println("ОШИБКА: Имя файла не может быть пустым.");
                continue;
            }
            
            File file = fileReader.findFile(filename);
            
            if (file == null || !file.exists()) {
                System.out.println("ФАЙЛ НЕ НАЙДЕН: " + filename);
                
                if (!askForRetry()) {
                    return new ArrayList<>(); // Возвращаем пустой список
                }
                continue;
            }
            
            try {
                List<Employee> employees = fileReader.readEmployeesFromFile(file);
                
                if (employees.isEmpty()) {
                    System.out.println("ПРЕДУПРЕЖДЕНИЕ: Не удалось загрузить сотрудников.");
                    
                    if (!askForRetry()) {
                        return new ArrayList<>();
                    }
                } else {
                    System.out.println("УСПЕШНО: Загружено " + employees.size() + " сотрудников");
                    return employees; // Возвращаем результат операции
                }
                
            } catch (IOException e) {
                System.out.println("ОШИБКА ЧТЕНИЯ: " + e.getMessage());
                
                if (!askForRetry()) {
                    return new ArrayList<>();
                }
            }
        }
    }
    
    private boolean askForRetry() {
        System.out.println("\n1 - Ввести другой файл");
        System.out.println("2 - Вернуться в меню");
        System.out.print("Выбор: ");
        
        while (true) {
            String choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                return true;
            } else if (choice.equals("2")) {
                return false;
            }
            System.out.print("Неверный выбор. Введите 1 или 2: ");
        }
    }
}
