package ru.practicum.ewmservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.EventLike;
import ru.practicum.ewmservice.user.model.User;

public interface EventLikeRepository extends JpaRepository<EventLike, Long>, EventLikeCustomRepository {
    EventLike findByEventAndLiker(Event event, User liker);
}
