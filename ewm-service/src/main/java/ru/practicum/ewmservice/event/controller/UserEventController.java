package ru.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.event.service.EventService;
import ru.practicum.ewmservice.utils.Create;
import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.utils.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
@Validated
public class UserEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getAllUserEvents(@PathVariable Long userId,
                                                @PositiveOrZero @RequestParam(
                                                        name = "from", defaultValue = "0") Integer from,
                                                @Positive @RequestParam(
                                                        name = "size", defaultValue = "10") Integer size) {
        final EwmPageRequest pageRequest = new EwmPageRequest(from, size, Sort.unsorted());
        return eventService.getAllUserEvents(userId, pageRequest);
    }


    @PostMapping
    public EventFullDto add(@PathVariable Long userId,
                            @Validated({Create.class}) @RequestBody NewEventDto newEventDto) {
        return eventService.createNewEvent(userId, newEventDto);
    }

    @PostMapping("/{eventId}/rate")
    @PatchMapping("/{eventId}/rate")
    public EventFullDto rateEvent(@PathVariable Long userId,
                                  @PathVariable Long eventId,
                                  @Max(value = 5, message = "Maximum rate value is 5.")
                                  @Min(value = 0, message = "Rating should be positive")
                                  @RequestParam(name = "rating") Float rateValue
    ) {
        return eventService.rateEvent(userId, eventId, rateValue);
    }

    @PatchMapping
    public EventFullDto update(@PathVariable Long userId,
                               @Validated({Update.class}) @RequestBody NewEventDto updateEventDto) {
        return eventService.updateEvent(userId, updateEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.getEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto rejectEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.rejectEventById(userId, eventId);
    }

}
