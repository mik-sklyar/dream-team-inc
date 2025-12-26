package business.perform;

import data.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Юнит-тесты для класса {@link ManualDataPerformStrategy}.
 *
 * <p><b>ВАЖНОЕ ПРИМЕЧАНИЕ:</b>
 * Эти тесты напрямую зависят от глобального статического сканера ({@code presentation.Utils.INPUT}).
 * Запускать отдельными тестами.
 */
class ManualDataPerformStrategyTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @Test
    void testPositivePath_CreatesEmployeeSuccessfully() {
        String simulatedInput = "1\n" +
                "Nechaev Sergey\n" +
                "nechaev@polin.com\n" +
                "password123\n";
        provideInput(simulatedInput);

        List<Employee> result = new ArrayList<>();
        ManualDataPerformStrategy strategy = new ManualDataPerformStrategy(result::addAll);

        strategy.execute();

        assertEquals(1, result.size());
        assertEquals("Nechaev Sergey", result.get(0).getName());
    }

    @Test
    void testNegativePath_RecoversFromInvalidInput() {

        String simulatedInput = "1\n" +
                "noname\n" +
                "invalid-email\n" +
                "noname@test.com\n" +
                "securepass\n";
        provideInput(simulatedInput);

        List<Employee> result = new ArrayList<>();
        ManualDataPerformStrategy strategy = new ManualDataPerformStrategy(result::addAll);

        strategy.execute();

        assertTrue(outContent.toString().contains("Ошибка валидации"));
        assertEquals(1, result.size());
        assertEquals("noname", result.get(0).getName());
        assertEquals("noname@test.com", result.get(0).getEmail());
    }

    @Test
    void testExitPath_ExitsOnZeroInput() {
        provideInput("0\n");

        List<Employee> result = new ArrayList<>();
        ManualDataPerformStrategy strategy = new ManualDataPerformStrategy(result::addAll);

        strategy.execute();

        assertTrue(result.isEmpty());
        assertTrue(outContent.toString().contains("Отмена операции"));
    }

    @Test
    void testPerformOperation_CreatesMultipleEmployees() {

        String simulatedInput = "2\n" +
                "First Employee\n" +
                "first@test.com\n" +
                "password123\n" +
                "Second Employee\n" +
                "second@test.com\n" +
                "password456\n";
        provideInput(simulatedInput);

        List<Employee> result = new ArrayList<>();
        ManualDataPerformStrategy strategy = new ManualDataPerformStrategy(result::addAll);

        strategy.execute();

        assertEquals(2, result.size(), "Должно быть создано два сотрудника.");
        assertEquals("First Employee", result.get(0).getName(), "Имя первого сотрудника должно быть корректным.");
        assertEquals("second@test.com", result.get(1).getEmail(), "Email второго сотрудника должен быть корректным.");
    }
}