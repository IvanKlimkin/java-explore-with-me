package ru.practicum.ewmservice.event.dto;

import lombok.*;
import ru.practicum.ewmservice.Create;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.user.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventShortDto {
    @NotBlank(groups = {Create.class})
    private String annotation;

    @NotNull(groups = {Create.class})
    private CategoryDto category;

    private Integer confirmedRequests;

    private String eventDate;

    private Long id;

    @NotNull(groups = {Create.class})
    private UserDto initiator;

    @NotNull(groups = {Create.class})
    private Boolean paid;

    @NotNull(groups = {Create.class})
    private String title;

    private Integer views;
}