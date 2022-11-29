package ru.practicum.ewmservice.user.dto;

import lombok.*;
import ru.practicum.ewmservice.Create;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserShortDto {
    private Long id;
    @NotBlank(groups = {Create.class})
    private String name;
}