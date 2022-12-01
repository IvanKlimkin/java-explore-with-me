package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.event.model.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventShortDto> getAllUserEvents(Long userId, EwmPageRequest pageRequest);

    EventFullDto createNewEvent(Long userId, NewEventDto eventDto);

    EventFullDto updateEvent(Long userId, NewEventDto updateEventDto);

    EventFullDto getEventById(Long userId, Long eventId);

    EventFullDto getEventByIdToAll(Long userId);

    EventFullDto rejectEventById(Long userId, Long eventId);

    List<EventFullDto> searchEvents(
            List<Long> users,
            List<State> states,
            List<Long> categories,
            LocalDateTime start,
            LocalDateTime end,
            EwmPageRequest pageRequest);

    List<EventFullDto> getFilteredEvents(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime start,
            LocalDateTime end,
            Boolean onlyAvailable,
            EwmPageRequest pageRequest);

    EventFullDto updateEventByAdmin(Long eventId, AdminUpdateEventRequest adminEvent);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);
}
