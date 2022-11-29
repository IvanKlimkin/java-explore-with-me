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
    List<Long> events;
    Long id;
    Boolean pinned;
    @NotBlank
    String title;
}
