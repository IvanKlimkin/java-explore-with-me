package ru.practicum.statsserver;

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
