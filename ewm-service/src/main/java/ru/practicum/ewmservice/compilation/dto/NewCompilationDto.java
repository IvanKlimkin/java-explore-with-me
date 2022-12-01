package ru.practicum.ewmservice.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewCompilationDto {
    private Long id;
    private Boolean pinned;
    @NotBlank
    private String title;
    private List<Long> events;
}
