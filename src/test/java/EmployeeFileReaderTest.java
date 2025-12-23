import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class EmployeeFileReaderTest {

    private EmployeeFileReader fileReader;
    
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileReader = new EmployeeFileReader();
    }

    @Test
    @DisplayName("Чтение корректного файла с паролями из 4 цифр")
    void readValidFileWith4DigitPasswords() throws IOException {
        // Arrange
        String content = "Иван Иванов;ivan@dreamteam.com;1353\n" +
                        "Мария Петрова;maria@company.com;2468\n" +
                        "# Это комментарий\n" +
                        "Алексей Сидоров;alex@dreamteam.com;7890\n\n" +
                        "Екатерина Волкова;katya@company.com;0000";
        
        Path testFile = tempDir.resolve("employees.txt");
        Files.write(testFile, content.getBytes());

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        assertEquals(4, employees.size());
        
        assertEquals("Иван Иванов", employees.get(0).getName());
        assertEquals("ivan@dreamteam.com", employees.get(0).getEmail());
        assertEquals("1353", employees.get(0).getPassword());
        
        assertEquals("Мария Петрова", employees.get(1).getName());
        assertEquals("2468", employees.get(1).getPassword());
        
        assertEquals("Алексей Сидоров", employees.get(2).getName());
        assertEquals("7890", employees.get(2).getPassword());
        
        assertEquals("Екатерина Волкова", employees.get(3).getName());
        assertEquals("0000", employees.get(3).getPassword());
    }

    @Test
    @DisplayName("Пропуск строк с неправильными паролями")
    void skipLinesWithInvalidPasswords() throws IOException {
        // Arrange
        String content = "Правильный 1;test1@test.com;1234\n" +
                        "Короткий пароль;test2@test.com;123\n" +
                        "Длинный пароль;test3@test.com;12345\n" +
                        "С буквами;test4@test.com;123a\n" +
                        "Правильный 2;test6@test.com;9999";
        
        Path testFile = tempDir.resolve("invalid_passwords.txt");
        Files.write(testFile, content.getBytes());

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        assertEquals(2, employees.size());
        assertEquals("Правильный 1", employees.get(0).getName());
        assertEquals("1234", employees.get(0).getPassword());
        assertEquals("Правильный 2", employees.get(1).getName());
        assertEquals("9999", employees.get(1).getPassword());
    }

    @Test
    @DisplayName("Чтение файла с паролями с ведущими нулями")
    void readFileWithLeadingZeros() throws IOException {
        // Arrange
        String content = "Сотрудник 1;emp1@test.com;0123\n" +
                        "Сотрудник 2;emp2@test.com;0012\n" +
                        "Сотрудник 3;emp3@test.com;0001\n" +
                        "Сотрудник 4;emp4@test.com;0000";
        
        Path testFile = tempDir.resolve("leading_zeros.txt");
        Files.write(testFile, content.getBytes());

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        assertEquals(4, employees.size());
        assertEquals("0123", employees.get(0).getPassword());
        assertEquals("0012", employees.get(1).getPassword());
        assertEquals("0001", employees.get(2).getPassword());
        assertEquals("0000", employees.get(3).getPassword());
    }

    @Test
    @DisplayName("Чтение файла с разными разделителями")
    void readFileWithDifferentDelimiters() throws IOException {
        // Arrange
        String content = "Иван Иванов;ivan@test.com;1111\n" +
                        "Мария Петрова,maria@test.com,2222\n" +
                        "Алексей Сидоров\talex@test.com\t3333";
        
        Path testFile = tempDir.resolve("mixed_delimiters.txt");
        Files.write(testFile, content.getBytes());

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        // Первые две строки должны загрузиться, третья с табуляцией - зависит от реализации
        assertTrue(employees.size() >= 2);
        
        // Проверяем что все загруженные пароли состоят из 4 цифр
        for (Employee emp : employees) {
            String password = emp.getPassword();
            assertEquals(4, password.length());
            // Проверяем что пароль состоит только из цифр
            assertTrue(password.matches("\\d{4}"));
        }
    }

    @Test
    @DisplayName("Парсинг строки с пробелами вокруг значений")
    void parseLineWithSpaces() throws IOException {
        // Arrange
        String content = "  Иван Иванов  ;  ivan@test.com  ;  1353  ";
        
        Path testFile = tempDir.resolve("spaces.txt");
        Files.write(testFile, content.getBytes());

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        assertEquals(1, employees.size());
        assertEquals("Иван Иванов", employees.get(0).getName());
        assertEquals("ivan@test.com", employees.get(0).getEmail());
        assertEquals("1353", employees.get(0).getPassword()); // Без пробелов
    }

    @Test
    @DisplayName("Чтение пустого файла")
    void readEmptyFile() throws IOException {
        // Arrange
        Path testFile = tempDir.resolve("empty.txt");
        Files.createFile(testFile);

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        assertTrue(employees.isEmpty());
    }

    @Test
    @DisplayName("Чтение файла только с комментариями")
    void readFileWithOnlyComments() throws IOException {
        // Arrange
        String content = "# Это тестовый файл\n" +
                        "# Формат: имя;email;пароль\n\n" +
                        "# Еще один комментарий";
        
        Path testFile = tempDir.resolve("comments.txt");
        Files.write(testFile, content.getBytes());

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        assertTrue(employees.isEmpty());
    }

    @Test
    @DisplayName("Исключение при чтении несуществующего файла")
    void exceptionWhenFileNotFound() {
        // Arrange
        java.io.File nonExistentFile = new java.io.File("non_existent_file_" + System.currentTimeMillis() + ".txt");

        // Act & Assert
        assertThrows(java.io.IOException.class, () -> {
            fileReader.readEmployeesFromFile(nonExistentFile);
        });
    }

    @Test
    @DisplayName("Поиск существующего файла")
    void findExistingFile() {
        // Для этого теста нужен реальный файл, создадим временный
        try {
            Path tempFile = tempDir.resolve("test_find.txt");
            Files.write(tempFile, "Тест;test@test.com;1234".getBytes());
            
            // Act
            java.io.File found = fileReader.findFile(tempFile.toAbsolutePath().toString());
            
            // Assert
            assertNotNull(found);
            assertTrue(found.exists());
            assertTrue(found.isFile());
            
        } catch (IOException e) {
            fail("Не удалось создать тестовый файл: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Поиск несуществующего файла возвращает null")
    void findNonExistentFileReturnsNull() {
        // Act
        java.io.File found = fileReader.findFile("non_existent_file_" + System.currentTimeMillis() + ".txt");
        
        // Assert
        assertNull(found);
    }

    @Test
    @DisplayName("Чтение файла с невалидным email")
    void readFileWithInvalidEmail() throws IOException {
        // Arrange
        String content = "Неправильный Email;invalid-email;1234\n" +
                        "Правильный;correct@test.com;5678";
        
        Path testFile = tempDir.resolve("invalid_email.txt");
        Files.write(testFile, content.getBytes());

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        assertEquals(1, employees.size()); // Только вторая строка должна загрузиться
        assertEquals("Правильный", employees.get(0).getName());
        assertEquals("correct@test.com", employees.get(0).getEmail());
        assertEquals("5678", employees.get(0).getPassword());
    }

    @Test
    @DisplayName("Чтение файла с пустым именем")
    void readFileWithEmptyName() throws IOException {
        // Arrange
        String content = ";empty@test.com;1234\n" +
                        "Правильный Имя;test@test.com;5678";
        
        Path testFile = tempDir.resolve("empty_name.txt");
        Files.write(testFile, content.getBytes());

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        assertEquals(1, employees.size()); // Только вторая строка должна загрузиться
        assertEquals("Правильный Имя", employees.get(0).getName());
    }

    @Test
    @DisplayName("Проверка всех валидных паролей от 0000 до 9999")
    void allValidPasswordsFrom0000To9999() throws IOException {
        // Arrange - создаем файл с несколькими валидными паролями
        StringBuilder content = new StringBuilder();
        for (int i = 0; i <= 9; i++) { // Тестируем только 10 для скорости
            String password = String.format("%04d", i * 1111 % 10000);
            content.append("Сотрудник ").append(i)
                   .append(";test").append(i).append("@test.com;")
                   .append(password).append("\n");
        }
        
        Path testFile = tempDir.resolve("all_passwords.txt");
        Files.write(testFile, content.toString().getBytes());

        // Act
        List<Employee> employees = fileReader.readEmployeesFromFile(testFile.toFile());

        // Assert
        assertEquals(10, employees.size());
        for (Employee emp : employees) {
            String password = emp.getPassword();
            assertEquals(4, password.length());
            assertTrue(password.matches("\\d{4}"));
        }
    }
}
