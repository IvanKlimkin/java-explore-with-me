package ru.practicum.ewmservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.State;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.utils.EwmPageRequest;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event>,
        EventCustomRepository {

    List<Event> findAllByInitiator(User initiator, EwmPageRequest pageRequest);

    Event findByIdAndAndState(Long eventId, State state);

}
