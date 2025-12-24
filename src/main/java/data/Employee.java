package data;

import java.security.SecureRandom;

public final class Employee {
    private final Integer order;
    private final Long id;
    private final String name;
    private final String email;
    private final String password;

    private Employee(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.id = builder.getId();
        this.order = builder.getOrder();
    }

    public Integer getOrder() {
        return order;
    }

    public Long getId() {
        return id;
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
        return "Сотрудник №" + order + " {id: " + id + ", имя: '" + name + "', email: " + email + "}";
    }

    public static class Builder {
        static private Integer order = -1;
        static private final SecureRandom uidProvider = new SecureRandom();
        private String name;
        private String email;
        private String password;

        private Integer getOrder() {
            order += 1;
            return order;
        }

        private Long getId() {
            return Integer.toUnsignedLong(uidProvider.nextInt());
        }

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
