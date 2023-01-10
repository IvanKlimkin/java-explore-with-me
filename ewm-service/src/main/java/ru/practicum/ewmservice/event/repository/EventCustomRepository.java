package ru.practicum.ewmservice.event.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.utils.EwmPageRequest;

public interface EventCustomRepository {
    Page<Event> searchEvents(BooleanExpression condition, EwmPageRequest pageRequest);

    Page<Event> getFilteredEvents(BooleanExpression condition, EwmPageRequest pageRequest);
}
