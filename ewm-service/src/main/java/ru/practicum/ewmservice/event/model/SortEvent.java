package ru.practicum.ewmservice.event.model;

import java.util.Objects;
import java.util.Optional;

public enum SortEvent {
    EVENT_DATE,
    VIEWS,
    RATING;

    public static Optional<SortEvent> from(String sort) {
        for (SortEvent value : SortEvent.values()) {
            if (Objects.equals(value.name(), sort)) {
                return Optional.of(value);
            }

        }
        return Optional.empty();
    }
}
