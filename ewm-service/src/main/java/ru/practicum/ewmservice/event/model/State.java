package ru.practicum.ewmservice.event.model;

import java.util.Objects;
import java.util.Optional;

public enum State {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static Optional<State> from(String status) {
        for (State value : State.values()) {
            if (Objects.equals(value.name(), status)) {
                return Optional.of(value);
            }

        }
        return Optional.empty();
    }
}
