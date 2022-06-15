package ru.practicum.scooter.api.model;

public class CreateCourierResponse {
    public Boolean ok;
    public String message;

    private final String BAD_REQUEST_MESSAGE = "Недостаточно данных для создания учетной записи";
    private final String CONFLICT_MESSAGE = "Этот логин уже используется";

    public CreateCourierResponse(Boolean ok) {
        this.ok = ok;
    }

    public CreateCourierResponse(String message) {
        this.message = message;
    }

    public String getBadRequestMessage() {
        return BAD_REQUEST_MESSAGE;
    }

    public String getConflictMessage() {
        return CONFLICT_MESSAGE;
    }
}
