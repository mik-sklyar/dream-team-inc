import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты Builder для Employee")
class EmployeeTest {

    @Nested
    @DisplayName("Позитивные сценарии")
    class PositiveScenarios {
        @Test
        @DisplayName("Должен создать сотрудника с корректными данными")
        void shouldCreateEmployeeWithValidData() {
            Employee employee = new Employee.Builder().setName("Анна Петрова").setEmail("anna@company.com").setPassword("secure123").build();

            assertNotNull(employee);
            assertEquals("Анна Петрова", employee.getName());
            assertEquals("anna@company.com", employee.getEmail());
            assertEquals("secure123", employee.getPassword());
            assertTrue(employee.toString().contains("Анна Петрова"));
        }
    }

    @Nested
    @DisplayName("Валидация имени")
    class NameValidation {
        @Test
        @DisplayName("Должен выбросить исключение при пустом имени")
        void shouldThrowWhenNameIsEmpty() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Employee.Builder().setName("").setEmail("test@example.com").setPassword("123456").build());
            assertTrue(exception.getMessage().contains("Имя не может быть пустым"));
        }

        @Test
        @DisplayName("Должен выбросить исключение при null имени")
        void shouldThrowWhenNameIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Employee.Builder().setName(null).setEmail("test@example.com").setPassword("123456").build());
            assertTrue(exception.getMessage().contains("Имя не может быть пустым"));
        }
    }

    @Nested
    @DisplayName("Валидация email")
    class EmailValidation {
        @Test
        @DisplayName("Должен принять корректный email")
        void shouldAcceptValidEmail() {
            Employee employee = new Employee.Builder().setName("Мария").setEmail("masha+work@domain.co.uk").setPassword("pass123").build();

            assertEquals("masha+work@domain.co.uk", employee.getEmail());
        }

        @Test
        @DisplayName("Должен отклонить email без @")
        void shouldRejectEmailWithoutAt() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Employee.Builder().setName("Мария").setEmail("mashadomain.com").setPassword("pass123").build());
            assertTrue(exception.getMessage().contains("Некорректный email"));
        }
    }

    @Nested
    @DisplayName("Валидация пароля")
    class PasswordValidation {
        @Test
        @DisplayName("Должен отклонить пароль короче 6 символов")
        void shouldRejectShortPassword() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Employee.Builder().setName("Иван").setEmail("ivan@example.com").setPassword("12345").build());
            assertTrue(exception.getMessage().contains("Пароль должен быть не менее 6 символов"));
        }

        @Test
        @DisplayName("Должен принять пароль из 6 символов")
        void shouldAcceptSixCharPassword() {
            Employee employee = new Employee.Builder().setName("Иван").setEmail("ivan@example.com").setPassword("123456").build();

            assertEquals("123456", employee.getPassword());
        }
    }

    @Nested
    @DisplayName("Проверка build()")
    class BuildValidation {
        @Test
        @DisplayName("Должен выбросить IllegalStateException при отсутствии имени")
        void shouldThrowWhenNameMissing() {
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Employee.Builder().setEmail("test@example.com").setPassword("123456").build());
            assertEquals("Требуется имя", exception.getMessage());
        }

        @Test
        @DisplayName("Должен выбросить IllegalStateException при отсутствии email")
        void shouldThrowWhenEmailMissing() {
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Employee.Builder().setName("Петр").setPassword("123456").build());
            assertEquals("Требуется email", exception.getMessage());
        }

        @Test
        @DisplayName("Должен выбросить IllegalStateException при отсутствии пароля")
        void shouldThrowWhenPasswordMissing() {
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Employee.Builder().setName("Петр").setEmail("petr@example.com").build());
            assertEquals("Требуется пароль", exception.getMessage());
        }
    }
}
