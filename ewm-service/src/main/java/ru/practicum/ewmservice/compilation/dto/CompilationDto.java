package ru.practicum.ewmservice.compilation.dto;

import lombok.*;
import ru.practicum.ewmservice.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompilationDto {
    List<EventShortDto> events;
    @NotNull
    Long id;
    @NotNull
    Boolean pinned;
    @NotBlank
    String title;
}
