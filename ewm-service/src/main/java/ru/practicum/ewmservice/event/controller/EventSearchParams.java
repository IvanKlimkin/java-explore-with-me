package ru.practicum.ewmservice.event.controller;

import lombok.*;
import ru.practicum.ewmservice.event.model.State;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventSearchParams {
    private List<Long> users;
    private List<State> states;
    private List<Long> categories;
    private LocalDateTime start;
    private LocalDateTime end;
}
