package ru.practicum.ewmservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.State;
import ru.practicum.ewmservice.user.model.User;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    List<Event> findAllByInitiator(User initiator, EwmPageRequest pageRequest);

    Event findByIdAndAndState(Long eventId, State state);

}
