package data;

import java.util.function.Function;

public enum EmployeeSortingField {
    ORDER("order", Employee::getOrderString, "Порядковый номер сотрудника"),
    ID("id", Employee::getIdString, "Уникальный идентификатор"),
    NAME("name", Employee::getName, "Имя"),
    EMAIL("email", Employee::getEmail, "Электронная почта"),
    PASSWORD("password", Employee::getPassword, "Пароль");

    private final String key;
    private final String description;
    private final Function<Employee, String> method;

    EmployeeSortingField(String key, Function<Employee, String> method, String description) {
        this.key = key;
        this.method = method;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public Function<Employee, String> getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
