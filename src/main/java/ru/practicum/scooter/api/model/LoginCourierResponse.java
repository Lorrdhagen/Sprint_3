package ru.practicum.scooter.api.model;

public class LoginCourierResponse {
    public int id;
    public String message;

    private final String BAD_REQUEST_MESSAGE = "Недостаточно данных для входа";
    private final String NOT_FOUND_MESSAGE = "Учетная запись не найдена";

    public LoginCourierResponse(int id) {
        this.id = id;
    }

    public LoginCourierResponse(String message) {
        this.message = message;
    }

    public String getBadRequestMessage() {
        return BAD_REQUEST_MESSAGE;
    }

    public String getNotFoundMessage() {
        return NOT_FOUND_MESSAGE;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ru.practicum.scooter.api.model.LoginCourierResponse{" +
                "id=" + id +
                '}';
    }
}
