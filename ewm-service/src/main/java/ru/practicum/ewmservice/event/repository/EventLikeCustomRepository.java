package ru.practicum.ewmservice.event.repository;

import com.querydsl.core.Tuple;
import ru.practicum.ewmservice.event.model.Event;

public interface EventLikeCustomRepository {
    Tuple getEventLikesInfo(Event event);
}
