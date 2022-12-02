package ru.practicum.ewmservice.event.dto;

import lombok.*;
import ru.practicum.ewmservice.utils.Create;
import ru.practicum.ewmservice.utils.Update;
import ru.practicum.ewmservice.event.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewEventDto {
    @NotBlank(groups = {Create.class, Update.class})
    private String annotation;

    @NotNull(groups = {Create.class, Update.class})
    private Long category;

    @NotNull(groups = {Create.class, Update.class})
    private String description;

    @NotNull(groups = {Create.class, Update.class})
    @TwoHourDelay(groups = {Create.class, Update.class})
    private String eventDate;

    @NotNull(groups = {Update.class})
    private Long eventId;

    @NotNull(groups = {Create.class})
    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    @NotNull(groups = {Create.class, Update.class})
    private String title;

}