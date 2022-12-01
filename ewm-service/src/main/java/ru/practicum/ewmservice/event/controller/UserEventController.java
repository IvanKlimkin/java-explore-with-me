package ru.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.utils.Create;
import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.utils.Update;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserEventController {
    private final EventService eventService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getAllUserEvents(@PathVariable Long userId,
                                                @PositiveOrZero @RequestParam(
                                                        name = "from", defaultValue = "0") Integer from,
                                                @Positive @RequestParam(
                                                        name = "size", defaultValue = "10") Integer size) {
        final EwmPageRequest pageRequest = new EwmPageRequest(from, size, Sort.unsorted());
        return eventService.getAllUserEvents(userId, pageRequest);
    }


    @PostMapping("/{userId}/events")
    public EventFullDto add(@PathVariable Long userId,
                            @Validated({Create.class}) @RequestBody NewEventDto newEventDto) {
        return eventService.createNewEvent(userId, newEventDto);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto update(@PathVariable Long userId,
                               @Validated({Update.class}) @RequestBody NewEventDto updateEventDto) {
        return eventService.updateEvent(userId, updateEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.getEventById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto rejectEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.rejectEventById(userId, eventId);
    }

}
