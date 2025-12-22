import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeFileReaderTest {

    private EmployeeFileReader fileReader;
    
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileReader = new EmployeeFileReader();
    }

    @Test
    @DisplayName("Чтение файла с паролями из 4 цифр")
    void readFileWith4DigitPasswords() throws IOException {
        // Создаем тестовый файл с паролями из 4 цифр
        String content = """
            Иван Иванов;ivan@dreamteam.com;1353
            Мария Петрова;maria@company.com;2468
            # Это комментарий
            Алексей Сидоров;alex@dreamteam.com;7890
            
            Екатерина Волкова;katya@company.com;0000
            """;
        
        Path testFile = tempDir.resolve("employees_4digit.txt");
        Files.writeString(testFile, content);

        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        assertEquals(4, employees.size());
        
        assertEquals("Иван Иванов", employees.get(0).getName());
        assertEquals("1353", employees.get(0).getPassword());
        
        assertEquals("Мария Петрова", employees.get(1).getName());
        assertEquals("2468", employees.get(1).getPassword());
        
        assertEquals("Алексей Сидоров", employees.get(2).getName());
        assertEquals("7890", employees.get(2).getPassword());
        
        assertEquals("Екатерина Волкова", employees.get(3).getName());
        assertEquals("0000", employees.get(3).getPassword());
    }

    @Test
    @DisplayName("Чтение файла с неправильными паролями - должны быть пропущены")
    void readFileWithInvalidPasswords() throws IOException {
        String content = """
            Правильный 1;test1@test.com;1234
            Короткий пароль;test2@test.com;123
            Длинный пароль;test3@test.com;12345
            С буквами;test4@test.com;123a
            С пробелом;test5@test.com;12 3
            Правильный 2;test6@test.com;9999
            """;
        
        Path testFile = tempDir.resolve("invalid_passwords.txt");
        Files.writeString(testFile, content);

        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Должны загрузиться только 2 корректные записи с паролями из 4 цифр
        assertEquals(2, employees.size());
        assertEquals("Правильный 1", employees.get(0).getName());
        assertEquals("1234", employees.get(0).getPassword());
        assertEquals("Правильный 2", employees.get(1).getName());
        assertEquals("9999", employees.get(1).getPassword());
    }

    @Test
    @DisplayName("Чтение файла с паролями с ведущими нулями")
    void readFileWithLeadingZeros() throws IOException {
        String content = """
            Сотрудник 1;emp1@test.com;0123
            Сотрудник 2;emp2@test.com;0012
            Сотрудник 3;emp3@test.com;0001
            Сотрудник 4;emp4@test.com;0000
            """;
        
        Path testFile = tempDir.resolve("leading_zeros.txt");
        Files.writeString(testFile, content);

        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        assertEquals(4, employees.size());
        assertEquals("0123", employees.get(0).getPassword());
        assertEquals("0012", employees.get(1).getPassword());
        assertEquals("0001", employees.get(2).getPassword());
        assertEquals("0000", employees.get(3).getPassword());
    }

    @Test
    @DisplayName("Чтение файла с разными разделителями и 4-значными паролями")
    void readFileWithDifferentDelimitersAnd4DigitPasswords() throws IOException {
        String content = """
            Иван Иванов;ivan@test.com;1111
            Мария Петрова,maria@test.com,2222
            Алексей Сидоров\talex@test.com\t3333
            Екатерина|Волкова|katya@test.com|4444
            """;
        
        Path testFile = tempDir.resolve("mixed_delimiters.txt");
        Files.writeString(testFile, content);

        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Должны загрузиться первые 3 (последний с | не загрузится, если не поддерживается)
        assertTrue(employees.size() >= 3);
        
        // Проверяем пароли
        for (Employee emp : employees) {
            String password = emp.getPassword();
            assertEquals(4, password.length());
            assertTrue(password.matches("\\d{4}"));
        }
    }

    @Test
    @DisplayName("Парсинг строки с лишними пробелами вокруг пароля")
    void parseLineWithSpacesAroundPassword() throws IOException {
        String content = "Иван Иванов;ivan@test.com;  1353  ";
        
        Path testFile = tempDir.resolve("spaces.txt");
        Files.writeString(testFile, content);

        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        assertEquals(1, employees.size());
        assertEquals("1353", employees.get(0).getPassword()); // Должен быть без пробелов
    }

    @Test
    @DisplayName("Пустой файл должен возвращать пустой список")
    void readEmptyFile() throws IOException {
        Path testFile = tempDir.resolve("empty.txt");
        Files.createFile(testFile);

        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        assertTrue(employees.isEmpty());
    }

    @Test
    @DisplayName("Файл только с комментариями")
    void readFileWithOnlyComments() throws IOException {
        String content = """
            # Это тестовый файл
            # Формат: имя;email;пароль(4 цифры)
            
            # Еще один комментарий
            """;
        
        Path testFile = tempDir.resolve("comments.txt");
        Files.writeString(testFile, content);

        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        assertTrue(employees.isEmpty());
    }
}
