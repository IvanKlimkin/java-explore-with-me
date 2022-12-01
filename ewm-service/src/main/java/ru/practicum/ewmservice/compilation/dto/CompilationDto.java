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
    @NotNull
    private Long id;
    @NotNull
    private Boolean pinned;
    @NotBlank
    private String title;
    private List<EventShortDto> events;
}
