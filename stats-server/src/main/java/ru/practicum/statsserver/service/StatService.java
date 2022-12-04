package ru.practicum.statsserver.service;

import ru.practicum.statsserver.dto.RequestDto;
import ru.practicum.statsserver.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    RequestDto saveRequest(RequestDto requestDto);

    List<ViewStats> getRequests(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Boolean unique
    );
}
