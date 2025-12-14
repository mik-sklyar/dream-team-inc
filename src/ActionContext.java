class ActionContext {
    public static class StrategyNotSetException extends IllegalStateException {
        public StrategyNotSetException() {
            super("Стратегия не установлена. Вызовите setStrategy() перед выполнением.");
        }

        public StrategyNotSetException(String message) {
            super(message);
        }
    }

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
}
