package ru.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.client.StatClient;
import ru.practicum.ewmservice.client.dto.RequestDto;
import ru.practicum.ewmservice.client.dto.ViewStats;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.model.SortEvent;
import ru.practicum.ewmservice.event.service.EventService;
import ru.practicum.ewmservice.utils.EwmPageRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final StatClient statClient;

    @GetMapping
    public List<EventFullDto> filteredEvents(@RequestParam(name = "text", required = false) String text,
                                             @RequestParam(name = "categories", required = false) List<Long> categories,
                                             @RequestParam(name = "paid", required = false) Boolean paid,
                                             @RequestParam(name = "rangeStart", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                             @RequestParam(name = "rangeEnd", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                             @RequestParam(name = "onlyAvailable", required = false,
                                                     defaultValue = "false") Boolean onlyAvailable,
                                             @RequestParam(name = "sort", required = false, defaultValue = "VIEWS")
                                             SortEvent sort,
                                             @PositiveOrZero @RequestParam(
                                                     name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(
                                                     name = "size", defaultValue = "10") Integer size,
                                             HttpServletRequest httpServletRequest) {
        EwmPageRequest pageRequest;
        if (sort == SortEvent.EVENT_DATE) {
            pageRequest = new EwmPageRequest(from, size, Sort.by("eventDate").descending());
        } else if (sort == SortEvent.RATING) {
            pageRequest = new EwmPageRequest(from, size, Sort.by("rating").descending());
        } else {
            pageRequest = new EwmPageRequest(from, size, Sort.by("views").descending());
        }
        EventFilterParams filterParams = new EventFilterParams(text, categories, paid, start, end, onlyAvailable, sort);
        statClient.saveRequest(new RequestDto(null, "ewm-service", httpServletRequest.getRequestURI(),
                httpServletRequest.getRemoteAddr(), LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        return eventService.getFilteredEvents(
                filterParams, pageRequest);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdToAll(@PathVariable Long eventId,
                                          HttpServletRequest httpServletRequest) {
        EventFullDto event = eventService.getEventByIdToAll(eventId);
        statClient.saveRequest(new RequestDto(null, "ewm-service", httpServletRequest.getRequestURI(),
                httpServletRequest.getRemoteAddr(), LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        List<ViewStats> eventStat = statClient.getStat(
                URLEncoder.encode(event
                        .getCreatedOn()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), StandardCharsets.UTF_8),
                URLEncoder.encode(
                        LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        , StandardCharsets.UTF_8),
                Collections.singletonList(httpServletRequest.getRequestURI()), false);
        if (!eventStat.isEmpty()) {
            event.setViews(eventStat.get(0).getHits());
        }
        return event;
    }
}
