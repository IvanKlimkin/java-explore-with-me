package ru.practicum.ewmservice.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.utils.EwmPageRequest;
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
@Slf4j
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationMapper compilationMapper;
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDto createNewCompilation(NewCompilationDto newCompilationDto) {
        log.info("Создание новой подборки событий");
        return compilationMapper.toCompilationDto(
                compilationRepository.save(
                        compilationMapper.toCompilation(newCompilationDto)));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        log.info("Удаление подборки событий");
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
        log.info("Добавление события в подборку");
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
        log.info("Удаление события из подборки");
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void pinCompilationToMain(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new ServerException("Подборка с таким ID отсутствует."));
        compilation.setPinned(true);
        compilationRepository.save(compilation);
        log.info("Подборка сохранена на главной странице.");
    }

    @Override
    @Transactional
    public void deleteCompilationFromMain(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new ServerException("Подборка с таким ID отсутствует."));
        compilation.setPinned(false);
        compilationRepository.save(compilation);
        log.info("Подборка откреплена с главной страницы.");
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        log.info("Получение подборки по Id");
        return compilationMapper.toCompilationDto(compilationRepository.findById(compId).orElseThrow(
                () -> new ServerException("Подборка с таким ID отсутствует.")));
    }

    @Override
    public List<CompilationDto> getCompilationsByCondition(Boolean pinned, EwmPageRequest pageRequest) {
        log.info("Получение подборки событий по условиям.");
        return compilationMapper.toCompilationDto(compilationRepository.findCompilationsByPinned(pinned, pageRequest));
    }
}
