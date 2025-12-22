import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ActionContextTest {

    private ActionContext context;
    
    @BeforeEach
    void setUp() {
        context = new ActionContext();
    }

    @Test
    @DisplayName("Выполнение стратегии после установки")
    void performWithStrategySet() {
        TestStrategy strategy = new TestStrategy();
        context.setStrategy(strategy);
        
        context.perform();
        
        assertTrue(strategy.wasExecuted());
    }

    @Test
    @DisplayName("Исключение при выполнении без стратегии")
    void performWithoutStrategyThrowsException() {
        ActionContext.StrategyNotSetException exception = assertThrows(
            ActionContext.StrategyNotSetException.class,
            () -> context.perform()
        );
        
        assertEquals("Стратегия не установлена. Вызовите setStrategy() перед выполнением.", 
                    exception.getMessage());
    }

    @Test
    @DisplayName("Замена стратегии")
    void replaceStrategy() {
        TestStrategy strategy1 = new TestStrategy();
        TestStrategy strategy2 = new TestStrategy();
        
        context.setStrategy(strategy1);
        context.setStrategy(strategy2);
        
        context.perform();
        
        assertFalse(strategy1.wasExecuted());
        assertTrue(strategy2.wasExecuted());
    }

    // Тестовая стратегия для проверки
    private static class TestStrategy implements ActionStrategy {
        private boolean executed = false;
        
        @Override
        public void execute() {
            executed = true;
        }
        
        public boolean wasExecuted() {
            return executed;
        }
    }
}
