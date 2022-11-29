package ru.practicum.ewmservice.compilation.service;

import ru.practicum.ewmservice.EwmPageRequest;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto createNewCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    void addEventToCompilation(Long compId, Long eventId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void pinCompilationToMain(Long compId);

    void deleteCompilationFromMain(Long compId);

    CompilationDto getCompilationById(Long compId);

    List<CompilationDto> getCompilationsByCondition(Boolean pinned, EwmPageRequest pageRequest);
}
