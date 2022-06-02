package ru.practicum.scooter.api.model;

public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ru.practicum.scooter.api.model.CourierCredentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
