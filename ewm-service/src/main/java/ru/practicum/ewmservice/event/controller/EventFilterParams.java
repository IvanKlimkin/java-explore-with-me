package ru.practicum.ewmservice.event.controller;

import lombok.*;
import ru.practicum.ewmservice.event.model.SortEvent;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventFilterParams {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean onlyAvailable;
    private SortEvent sort;
}
