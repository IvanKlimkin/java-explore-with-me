package ru.practicum.ewmservice.event.dto;

import lombok.*;
import ru.practicum.ewmservice.Update;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateEventDto {
    @NotBlank(groups = {Update.class})
    private String annotation;

    @NotNull(groups = {Update.class})
    private Long category;

    @NotNull(groups = {Update.class})
    private String description;

    @NotNull(groups = {Update.class})
    @FutureOrPresent(groups = {Update.class})
    @TwoHourDelay
    private LocalDateTime eventDate;
    @NotNull(groups = {Update.class})
    private Long eventId;

    private Boolean paid;

    private Integer participantLimit;

    @NotNull(groups = {Update.class})
    private String title;

}

