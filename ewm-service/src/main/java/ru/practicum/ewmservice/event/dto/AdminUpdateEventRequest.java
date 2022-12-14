package ru.practicum.ewmservice.event.dto;

import lombok.*;
import ru.practicum.ewmservice.event.model.Location;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminUpdateEventRequest {
    private String annotation;

    private Long category;

    private String description;

    private String eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;
}
