package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.controller.EventFilterParams;
import ru.practicum.ewmservice.event.controller.EventSearchParams;
import ru.practicum.ewmservice.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.utils.EwmPageRequest;

import java.util.List;

public interface EventService {

    List<EventShortDto> getAllUserEvents(Long userId, EwmPageRequest pageRequest);

    EventFullDto createNewEvent(Long userId, NewEventDto eventDto);

    EventFullDto rateEvent(Long userId, Long eventId, Float likeValue);

    EventFullDto updateEvent(Long userId, NewEventDto updateEventDto);

    EventFullDto updateRateEvent(Long userId, Long eventId, Float rateValue);

    void deleteRate(Long userId, Long eventId);

    EventFullDto getEventById(Long userId, Long eventId);

    EventFullDto getEventByIdToAll(Long userId);

    EventFullDto rejectEventById(Long userId, Long eventId);

    List<EventFullDto> searchEvents(
            EventSearchParams prams,
            EwmPageRequest pageRequest);

    List<EventFullDto> getFilteredEvents(
            EventFilterParams filterParams,
            EwmPageRequest pageRequest);

    EventFullDto updateEventByAdmin(Long eventId, AdminUpdateEventRequest adminEvent);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);
}
