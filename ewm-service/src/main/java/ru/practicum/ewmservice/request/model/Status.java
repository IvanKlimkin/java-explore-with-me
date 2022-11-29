package ru.practicum.ewmservice.request.model;

import java.util.Objects;
import java.util.Optional;

public enum Status {
    CANCELED,
    CONFIRMED,
    PENDING,
    REJECTED;

    public static Optional<ru.practicum.ewmservice.event.model.State> from(String status) {
        for (ru.practicum.ewmservice.event.model.State value : ru.practicum.ewmservice.event.model.State.values()) {
            if (Objects.equals(value.name(), status)) {
                return Optional.of(value);
            }

        }
        return Optional.empty();
    }
}