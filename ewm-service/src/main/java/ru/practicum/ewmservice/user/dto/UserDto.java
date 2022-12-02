package ru.practicum.ewmservice.user.dto;


import lombok.*;
import ru.practicum.ewmservice.utils.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    @NotBlank(groups = {Create.class})
    @Email(groups = {Create.class})
    private String email;
    @NotBlank(groups = {Create.class})
    private String name;
}
