import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты Builder для Employee")
class EmployeeTest {

    @Nested
    @DisplayName("Позитивные сценарии")
    class PositiveScenarios {
        @Test
        @DisplayName("Должен создать сотрудника с корректными данными")
        void shouldCreateEmployeeWithValidData() {
            Employee employee = new Employee.Builder()
                    .setName("Анна Петрова")
                    .setEmail("anna@company.com")
                    .setPassword("secure123")
                    .build();

            assertNotNull(employee);
            assertEquals("Анна Петрова", employee.getName());
            assertEquals("anna@company.com", employee.getEmail());
            assertEquals("secure123", employee.getPassword());
            assertTrue(employee.toString().contains("Анна Петрова"));
        }
    }

    @Nested
    @DisplayName("Проверка build()")
    class BuildValidation {
        @Test
        @DisplayName("Должен выбросить IllegalStateException при отсутствии имени")
        void shouldThrowWhenNameMissing() {
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> new Employee.Builder()
                            .setEmail("test@example.com")
                            .setPassword("123456")
                            .build()
            );
            assertEquals("Требуется имя", exception.getMessage());
        }

        @Test
        @DisplayName("Должен выбросить IllegalStateException при отсутствии email")
        void shouldThrowWhenEmailMissing() {
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> new Employee.Builder()
                            .setName("Петр")
                            .setPassword("123456")
                            .build()
            );
            assertEquals("Требуется email", exception.getMessage());
        }

        @Test
        @DisplayName("Должен выбросить IllegalStateException при отсутствии пароля")
        void shouldThrowWhenPasswordMissing() {
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> new Employee.Builder()
                            .setName("Петр")
                            .setEmail("petr@example.com")
                            .build()
            );
            assertEquals("Требуется пароль", exception.getMessage());
        }
    }
}
