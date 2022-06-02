package ru.practicum.scooter.api.model;

public class CreateOrderResponse {
    public int track;

    public CreateOrderResponse(int track) {
        this.track = track;
    }

    public int getTrack() {
        return track;
    }

    @Override
    public String toString() {
        return "ru.practicum.scooter.api.model.CreateOrderResponse{" +
                "track=" + track +
                '}';
    }
}
