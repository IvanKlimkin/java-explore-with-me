package ru.practicum.ewmservice.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.request.model.ParticipationRequest;
import ru.practicum.ewmservice.request.model.Status;
import ru.practicum.ewmservice.user.model.User;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findParticipationRequestsByRequester(User user);

    List<ParticipationRequest> findParticipationRequestsByEvent(Event event);

    List<ParticipationRequest> findParticipationRequestsByEventAndStatus(Event event, Status status);

    ParticipationRequest findParticipationRequestsByRequesterAndEvent(User user, Event event);

}
