package ru.practicum.ewmservice.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoryRepository;
import ru.practicum.ewmservice.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.QEvent;
import ru.practicum.ewmservice.event.model.State;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.event.repository.LocationRepository;
import ru.practicum.ewmservice.exception.ServerException;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;

    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventShortDto> getAllUserEvents(Long userId, EwmPageRequest pageRequest) {
        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Пользователь с таким ID отсутствует."));
        log.info("Запрос получения всех мероприятий пользователя.");
        return eventMapper.toShortDto(eventRepository.findAllByInitiator(initiator, pageRequest));
    }

    @Override
    @Transactional
    public EventFullDto createNewEvent(Long userId, NewEventDto eventDto) {
        Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(
                () -> new ServerException("Категория с таким ID отсутствует."));
        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Пользователь с таким ID отсутствует."));
        log.info("Запрос создания нового мероприятия.");
        locationRepository.save(eventDto.getLocation());
        return eventMapper.toFullDto(
                eventRepository.save(
                        eventMapper.toEvent(eventDto, category, initiator)));
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long userId, NewEventDto updateEventDto) {
        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Пользователь с таким ID отсутствует."));
        Event event = eventRepository.findById(updateEventDto.getEventId()).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        log.info("Обновление мероприятия.");
        if (event.getInitiator().getId().equals(initiator.getId())) {
            if (event.getState() !=State.PUBLISHED) {
                event.setAnnotation(updateEventDto.getAnnotation());
                event.setCategory(categoryRepository.findById(updateEventDto.getCategory()).orElseThrow(
                        () -> new ServerException("Категория с таким ID отсутствует.")));
                event.setDescription(updateEventDto.getDescription());
                if (updateEventDto.getPaid() != null) {
                    event.setPaid(updateEventDto.getPaid());
                }
                if (updateEventDto.getParticipantLimit() != null) {
                    event.setParticipantLimit(updateEventDto.getParticipantLimit());
                }
                event.setTitle(updateEventDto.getTitle());
                event.setState(State.PENDING);
            }

        } else {
            throw new ServerException("Только пользователь создавший событие имеет право на обновление.");
        }
        return eventMapper.toFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto getEventById(Long userId, Long eventId) {
        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Пользователь с таким ID отсутствует."));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        log.info("Запрос получения мероприятия по Id.");
        if (event.getInitiator().getId().equals(initiator.getId())) {
            return eventMapper.toFullDto(event);
        } else {
            throw new ServerException("Только пользователь создавший событие имеет право на получение информации.");
        }
    }

    @Override
    public EventFullDto getEventByIdToAll(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        log.info("Запрос получения опубликованного мероприятия по Id.");
        if (event.getState() != State.PUBLISHED) {
            throw new ServerException("Событие не опубликовано.");
        }
        return eventMapper.toFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto rejectEventById(Long userId, Long eventId) {
        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Пользователь с таким ID отсутствует."));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        log.info("Запрос на отклонение события");
        if (event.getInitiator().getId().equals(initiator.getId())) {
            if (event.getState().equals(State.PENDING)) {
                event.setState(State.CANCELED);
                return eventMapper.toFullDto(eventRepository.save(event));
            } else {
                throw new ServerException("Событие уже прошло модерацию.");
            }
        } else {
            throw new ServerException("Только пользователь создавший событие имеет право на отклонение.");
        }
    }

    @Override
    public List<EventFullDto> searchEvents(List<Long> users,
                                           List<State> states,
                                           List<Long> categories,
                                           LocalDateTime start,
                                           LocalDateTime end,
                                           EwmPageRequest pageRequest) {
        log.info("Поиск события по параметрам запроса.");
        BooleanExpression result = null;
        BooleanExpression condition;
        if (users != null) {
            condition = QEvent.event.initiator.id.in(users);
            result = condition;
        }
        if (states != null) {
            condition = QEvent.event.state.in(states);
            result = result == null ? condition : result.and(condition);
        }
        if (categories != null) {
            condition = QEvent.event.category.id.in(categories);
            result = result == null ? condition : result.and(condition);
        }
        if ((start != null || end != null)) {
            condition = QEvent.event.eventDate.between(start, end);
            result = result == null ? condition : result.and(condition);
        }
        if (result == null) {
            result = Expressions.asBoolean(true).isTrue();
        }
        Page<Event> foundEvents = eventRepository.findAll(result, pageRequest);
        if (foundEvents != null) {
            return eventMapper.toFullDto(foundEvents.getContent());
        }
        return Collections.emptyList();
    }

    @Override
    public List<EventFullDto> getFilteredEvents(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime start,
            LocalDateTime end,
            Boolean onlyAvailable,
            EwmPageRequest pageRequest) {
        log.info("Запрос получения мероприятия с филтрацией по параметрам.");
        BooleanExpression result = null;
        BooleanExpression condition;
        result = QEvent.event.state.eq(State.PUBLISHED);
        if (text != null) {
            condition = QEvent.event.annotation.toLowerCase().contains(text.toLowerCase())
                    .or(QEvent.event.description.toLowerCase().contains(text.toLowerCase()));
            result = result == null ? condition : result.and(condition);
        }
        if (categories != null) {
            condition = QEvent.event.category.id.in(categories);
            result = result == null ? condition : result.and(condition);
        }
        if (paid != null) {
            condition = QEvent.event.paid.eq(paid);
            result = result == null ? condition : result.and(condition);
        }
        if (start != null && end != null) {
            condition = QEvent.event.eventDate.between(start, end);
        } else {
            condition = QEvent.event.eventDate.after(LocalDateTime.now());
        }
        result = result == null ? condition : result.and(condition);
        if (!onlyAvailable.equals(false)) {
            condition = QEvent.event.confirmedRequests.lt(QEvent.event.participantLimit);
            result = result == null ? condition : result.and(condition);
        }
        Page<Event> foundEvents = eventRepository.findAll(result, pageRequest);
        if (foundEvents != null) {
            return eventMapper.toFullDto(foundEvents.getContent());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId, AdminUpdateEventRequest adminEvent) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        Category category = categoryRepository.findById(adminEvent.getCategory()).orElseThrow(
                () -> new ServerException("Категория с таким ID отсутствует."));
        log.info("Изменения события Админом");
        if (adminEvent.getAnnotation() != null) {
            event.setAnnotation(adminEvent.getAnnotation());
        }
        if (adminEvent.getCategory() != null) {
            event.setCategory(category);
        }
        if (adminEvent.getDescription() != null) {
            event.setDescription(adminEvent.getDescription());
        }
        if (adminEvent.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(
                    adminEvent.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (adminEvent.getLocation() != null) {
            event.setLocation(adminEvent.getLocation());
        }
        if (adminEvent.getPaid() != null) {
            event.setPaid(adminEvent.getPaid());
        }
        if (adminEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(adminEvent.getParticipantLimit());
        }
        if (adminEvent.getRequestModeration() != null) {
            event.setRequestModeration(adminEvent.getRequestModeration());
        }
        if (adminEvent.getTitle() != null) {
            event.setTitle(adminEvent.getTitle());
        }
        return eventMapper.toFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        log.info("Публикация события");
        if (event.getState() == State.PENDING && event.getEventDate().isAfter(LocalDateTime.now().plusHours(1))) {
            event.setState(State.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
            return eventMapper.toFullDto(eventRepository.save(event));
        } else {
            throw new ServerException("Проверьте статус или дату мероприятия.");
        }
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        log.info("Запрос на отказ в размещении мероприятия.");
        if (event.getState() != State.PUBLISHED) {
            event.setState(State.CANCELED);
            return eventMapper.toFullDto(eventRepository.save(event));
        } else {
            throw new ServerException("Мероприятие уже опубликовано/отменено.");
        }
    }
}
