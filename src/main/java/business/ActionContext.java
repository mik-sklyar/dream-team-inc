package business;

public class ActionContext {
    private ActionStrategy strategy;

    public void setStrategy(ActionStrategy strategy) {
        this.strategy = strategy;
    }

    public void perform() {
        if (strategy == null) {
            throw new StrategyNotSetException();
        }
        strategy.execute();
    }

    public static class StrategyNotSetException extends IllegalStateException {
        public StrategyNotSetException() {
            super("Стратегия не установлена. Вызовите setStrategy() перед выполнением.");
        }
    }
}
