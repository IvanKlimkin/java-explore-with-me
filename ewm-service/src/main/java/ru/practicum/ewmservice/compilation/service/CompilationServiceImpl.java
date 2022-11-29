package ru.practicum.ewmservice.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.EwmPageRequest;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.compilation.repository.CompilationRepository;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.exception.ServerException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationMapper compilationMapper;
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDto createNewCompilation(NewCompilationDto newCompilationDto) {
        return compilationMapper.toCompilationDto(
                compilationRepository.save(
                        compilationMapper.toCompilation(newCompilationDto)));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new ServerException("Подборка с таким ID отсутствует."));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        List<Event> compEvents = compilation.getEvents();
        compEvents.add(event);
        compilation.setEvents(compEvents);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new ServerException("Подборка с таким ID отсутствует."));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        List<Event> compEvents = compilation.getEvents();
        compEvents.remove(event);
        compilation.setEvents(compEvents);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void pinCompilationToMain(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new ServerException("Подборка с таким ID отсутствует."));
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void deleteCompilationFromMain(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new ServerException("Подборка с таким ID отсутствует."));
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        return compilationMapper.toCompilationDto(compilationRepository.findById(compId).orElseThrow(
                () -> new ServerException("Подборка с таким ID отсутствует.")));
    }

    @Override
    public List<CompilationDto> getCompilationsByCondition(Boolean pinned, EwmPageRequest pageRequest) {
        return compilationMapper.toCompilationDto(compilationRepository.findCompilationsByPinned(pinned, pageRequest));
    }
}
