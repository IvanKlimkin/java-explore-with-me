package ru.practicum.ewmservice.category.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDto {
    private Long id;
    @NotBlank
    private String name;
}
