import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FileDataPerformStrategy implements ActionStrategy {
    
    private final EmployeeFileReader fileReader;
    
    public FileDataPerformStrategy() {
        this.fileReader = new EmployeeFileReader();
    }
    
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== ЗАГРУЗКА СОТРУДНИКОВ ИЗ ФАЙЛА ===");
            System.out.println("Введите имя файла (например: employees.txt)");
            System.out.println("Или полный путь к файлу");
            System.out.print(">> ");
            
            String filename = scanner.nextLine().trim();
            
            if (filename.isEmpty()) {
                System.out.println("ОШИБКА: Имя файла не может быть пустым.");
                continue;
            }
            
            
            File file = fileReader.findFile(filename);
            
            if (file == null || !file.exists()) {
                System.out.println("ФАЙЛ НЕ НАЙДЕН: " + filename);
                System.out.println("Убедитесь, что файл находится в одном из мест:");
                System.out.println("1. Папка 'src/main/resources/' (для Maven/Gradle проектов)");
                System.out.println("2. Папка 'src/resources/'");
                System.out.println("3. Папка 'resources/' в корне проекта");
                System.out.println("4. Корень проекта");
                System.out.println("5. Или укажите полный путь к файлу");
                
                if (!askForRetry(scanner)) {
                    System.out.println("Возвращаемся в главное меню...");
                    return;
                }
                continue;
            }
            
            if (!file.canRead()) {
                System.out.println("ОШИБКА: Нет прав на чтение файла: " + file.getAbsolutePath());
                if (!askForRetry(scanner)) {
                    System.out.println("Возвращаемся в главное меню...");
                    return;
                }
                continue;
            }
            
            try {
                
                List<Employee> employees = fileReader.readEmployeesFromFile(file);
                
                if (employees.isEmpty()) {
                    System.out.println("ПРЕДУПРЕЖДЕНИЕ: Не удалось загрузить ни одного сотрудника.");
                    System.out.println("Проверьте формат файла. Ожидается: Имя;email;пароль");
                    
                    if (!askForRetry(scanner)) {
                        System.out.println("Возвращаемся в главное меню...");
                        return;
                    }
                } else 
                    displayLoadedEmployees(employees);
                    waitForEnter(scanner);
                    System.out.println("Возвращаемся в главное меню...");
                    return;
                }
                
            } catch (IOException e) {
                System.out.println("ОШИБКА ЧТЕНИЯ ФАЙЛА: " + e.getMessage());
                
                if (!askForRetry(scanner)) {
                    System.out.println("Возвращаемся в главное меню...");
                    return;
                }
            }
        }
    }
    
    private boolean askForRetry(Scanner scanner) {
        System.out.println("\nВыберите действие:");
        System.out.println("1 - Ввести другое имя файла");
        System.out.println("2 - Вернуться в главное меню");
        System.out.print("Ваш выбор [1-2]: ");
        
        while (true) {
            String choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                return true;
            } else if (choice.equals("2")) {
                return false;
            } else {
                System.out.print("Неверный выбор. Введите 1 или 2: ");
            }
        }
    }
    
    private void displayLoadedEmployees(List<Employee> employees) {
        System.out.println("\n==========================================");
        System.out.println("УСПЕШНО ЗАГРУЖЕНО СОТРУДНИКОВ: " + employees.size());
        System.out.println("==========================================");
        System.out.println();
        System.out.println("СПИСОК ЗАГРУЖЕННЫХ СОТРУДНИКОВ:");
        System.out.println("------------------------------------------");
        
        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            System.out.printf("%3d. Имя: %-20s Email: %-25s%n", 
                            i + 1, emp.getName(), emp.getEmail());
        }
        
        System.out.println("------------------------------------------");
    }
    
    private void waitForEnter(Scanner scanner) {
        System.out.println("\nНажмите Enter для продолжения...");
        scanner.nextLine();
    }
}
