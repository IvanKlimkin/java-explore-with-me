package ru.practicum.ewmservice.event.dto;


import lombok.*;
import ru.practicum.ewmservice.Create;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.model.State;
import ru.practicum.ewmservice.user.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventFullDto {
    @NotBlank(groups = {Create.class})
    private String annotation;

    @NotNull(groups = {Create.class})
    private CategoryDto category;

    private Integer confirmedRequests;

    private LocalDateTime createdOn;

    private String description;

    private String eventDate;

    private Long id;

    @NotNull(groups = {Create.class})
    private UserDto initiator;

    @NotNull(groups = {Create.class})
    private Location location;

    @NotNull(groups = {Create.class})
    private Boolean paid;

    private Integer participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    @NotNull(groups = {Create.class})
    private String title;

    private Integer views;
}