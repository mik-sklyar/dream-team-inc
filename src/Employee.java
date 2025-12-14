public final class Employee {
    private final String name;
    private final String email;
    private final String password;

    private Employee(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Сотрудник {" + "имя :'" + name + '\'' + ", email :'" + email + '\'' + '}';
    }

    public static class Builder {
        private String name;
        private String email;
        private String password;

        public Builder setName(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Имя не может быть пустым");
            }
            this.name = name.trim();
            return this;
        }

        public Builder setEmail(String email) {
            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new IllegalArgumentException("Некорректный email: " + email);
            }
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            if (password == null || password.length() < 6) {
                throw new IllegalArgumentException("Пароль должен быть не менее 6 символов");
            }
            this.password = password;
            return this;
        }

        public Employee build() {
            if (name == null) {
                throw new IllegalStateException("Требуется имя");
            }
            if (email == null) {
                throw new IllegalStateException("Требуется email");
            }
            if (password == null) {
                throw new IllegalStateException("Требуется пароль");
            }
            return new Employee(this);
        }
    }
}
