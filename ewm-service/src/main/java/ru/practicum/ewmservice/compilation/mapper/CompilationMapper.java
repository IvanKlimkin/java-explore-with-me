package ru.practicum.ewmservice.compilation.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.exception.ServerException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public Compilation toCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = newCompilationDto.getEvents() == null ? Collections.emptyList() :
                newCompilationDto.getEvents().stream()
                        .map(i -> eventRepository.findById(i).orElseThrow(
                                () -> new ServerException("Событие с таким eventID отсутствует.")))
                        .collect(Collectors.toList());
        return new Compilation(
                newCompilationDto.getId(),
                newCompilationDto.getPinned(),
                newCompilationDto.getTitle(),
                events
        );
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle(),
                eventMapper.toShortDto(compilation.getEvents())
        );
    }

    public List<CompilationDto> toCompilationDto(Iterable<Compilation> compilations) {
        List<CompilationDto> dtos = new ArrayList<>();
        for (Compilation compilation : compilations) {
            dtos.add(toCompilationDto(compilation));
        }
        return dtos;
    }

}