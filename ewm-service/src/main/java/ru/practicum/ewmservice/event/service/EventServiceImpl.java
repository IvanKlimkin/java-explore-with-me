package ru.practicum.ewmservice.event.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoryRepository;
import ru.practicum.ewmservice.event.controller.EventFilterParams;
import ru.practicum.ewmservice.event.controller.EventSearchParams;
import ru.practicum.ewmservice.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.*;
import ru.practicum.ewmservice.event.repository.EventLikeRepository;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.event.repository.LocationRepository;
import ru.practicum.ewmservice.exception.ServerException;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.repository.UserRepository;
import ru.practicum.ewmservice.utils.EwmPageRequest;

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
    private final EventLikeRepository eventLikeRepository;

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
        log.info("Create new event request");
        Location location = locationRepository.save(
                new Location(0L, eventDto.getLocation().getLat(), eventDto.getLocation().getLon()));
        return eventMapper.toFullDto(
                eventRepository.save(
                        eventMapper.toEvent(eventDto, category, initiator, location)));
    }

    @Override
    @Transactional
    public EventFullDto rateEvent(Long userId, Long eventId, Float likeValue) {
        log.info("Rate event request");
        User liker = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Wrong user Id."));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Wrong event Id."));
        if (event.getState() != State.PUBLISHED) {
            throw new ServerException("Only published events available to rate.");
        }
        EventLike eventLike = eventLikeRepository.findByEventAndLiker(event, liker);
        if (eventLike == null) {
            eventLike = new EventLike(0L, liker, event, likeValue);
        } else {
            throw new ServerException("Rate from such user exist? try PATCH or DELETE request");
        }
        eventLikeRepository.save(eventLike);
        return eventMapper.toFullDto(eventRepository.save(calculateRating(event)));
    }

    @Override
    @Transactional
    public EventFullDto updateRateEvent(Long userId, Long eventId, Float rateValue) {
        log.info("Update rate value request");
        User liker = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Wrong user Id."));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Wrong event Id."));
        EventLike eventLike = eventLikeRepository.findByEventAndLiker(event, liker);
        if (eventLike == null) {
            throw new ServerException("Rate of such event doesn't exist.");
        } else {
            eventLike.setLikeValue(rateValue);
        }
        return eventMapper.toFullDto(eventRepository.save(calculateRating(event)));
    }

    @Override
    @Transactional
    public void deleteRate(Long userId, Long eventId) {
        log.info("Delete event rate request");
        User liker = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Wrong user Id."));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Wrong event Id."));

        EventLike likeToDelete = eventLikeRepository.findByEventAndLiker(event, liker);
        if (likeToDelete != null) {
            eventLikeRepository.delete(likeToDelete);
        }
    }

    private Event calculateRating(Event event) {
        Tuple likeInfo = eventLikeRepository.getEventLikesInfo(event);
        if (likeInfo != null) {
            event.setRating(likeInfo.get(0, Float.class) / likeInfo.get(1, Long.class));
        }
        return event;
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
            if (event.getState() != State.PUBLISHED) {
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
    public List<EventFullDto> searchEvents(EventSearchParams params,
                                           EwmPageRequest pageRequest) {
        log.info("Search events by request parameters.");
        QEvent event = QEvent.event;
        BooleanExpression paramsPredicate = null;
        if (!params.getUsers().contains(0L)) {
            paramsPredicate = event.initiator.id.in(params.getUsers());
        }
        if (!params.getCategories().contains(0L)) {
            if (paramsPredicate == null) {
                paramsPredicate = event.category.id.in(params.getCategories());
            } else {
                paramsPredicate.and(event.category.id.in(params.getCategories()));
            }
        }
        if (params.getStates() != null) {
            if (paramsPredicate == null) {
                paramsPredicate = event.state.in(params.getStates());
            } else {
                paramsPredicate.and(event.state.in(params.getStates()));
            }
        }
        if (params.getStart() != null && params.getEnd() != null) {
            if (paramsPredicate == null) {
                paramsPredicate = event.eventDate.between(params.getStart(), params.getEnd());
            } else {
                paramsPredicate.and(event.eventDate.between(params.getStart(), params.getEnd()));
            }
        }

        Page<Event> foundEvents = eventRepository.searchEvents(paramsPredicate, pageRequest);
        if (foundEvents != null) {
            return eventMapper.toFullDto(foundEvents.getContent());
        }
        return Collections.emptyList();
    }

    @Override
    public List<EventFullDto> getFilteredEvents(EventFilterParams filterParams, EwmPageRequest pageRequest) {
        log.info("Get events filtered by parameter.");
        QEvent event = QEvent.event;
        BooleanExpression result = null;
        BooleanExpression condition;
        result = event.state.eq(State.PUBLISHED);
        if (filterParams.getText() != null) {
            condition = event.annotation.toLowerCase().contains(filterParams.getText().toLowerCase())
                    .or(event.description.toLowerCase().contains(filterParams.getText().toLowerCase()));
            result = result == null ? condition : result.and(condition);
        }
        if (filterParams.getCategories() != null) {
            condition = event.category.id.in(filterParams.getCategories());
            result = result == null ? condition : result.and(condition);
        }
        if (filterParams.getPaid() != null) {
            condition = event.paid.eq(filterParams.getPaid());
            result = result == null ? condition : result.and(condition);
        }
        if (filterParams.getStart() != null && filterParams.getEnd() != null) {
            condition = event.eventDate.between(filterParams.getStart(), filterParams.getEnd());
        } else {
            condition = event.eventDate.after(LocalDateTime.now());
        }
        result = result == null ? condition : result.and(condition);
        if (!filterParams.getOnlyAvailable().equals(false)) {
            condition = event.confirmedRequests.lt(event.participantLimit);
            result = result == null ? condition : result.and(condition);
        }
        Page<Event> foundEvents = eventRepository.getFilteredEvents(result, pageRequest);
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
