package ru.practicum.ewmservice.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.exception.ServerException;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.mapper.RequestMapper;
import ru.practicum.ewmservice.request.model.ParticipationRequest;
import ru.practicum.ewmservice.request.model.Status;
import ru.practicum.ewmservice.request.repository.RequestRepository;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    @Override
    public List<ParticipationRequestDto> getAllUserOwnRequests(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Пользователь с таким ID отсутствует."));
        return requestMapper.toDto(requestRepository.findParticipationRequestsByRequester(user));
    }

    @Override
    public List<ParticipationRequestDto> getAllUserEventRequests(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Пользователь с таким ID отсутствует."));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        if (!Objects.equals(event.getInitiator(), user)) {
            throw new ServerException("Только пользователь создавший событие имеет право на просмотр запросов");
        }
        return requestMapper.toDto(requestRepository.findParticipationRequestsByEvent(event));
    }

    @Override
    @Transactional
    public ParticipationRequestDto createNewRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Пользователь с таким ID отсутствует."));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ServerException("Событие с таким eventID отсутствует."));
        if (requestRepository.findParticipationRequestsByRequesterAndEvent(user, event) == null &&
                event.getInitiator().getId() != userId &&
                event.getConfirmedRequests() < event.getParticipantLimit()) {
            if (event.getRequestModeration()) {
                return requestMapper.toDto(
                        requestRepository.save(
                                requestMapper.toParticipationRequest(user, event, Status.PENDING)));
            } else {
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                eventRepository.save(event);
                return requestMapper.toDto(
                        requestRepository.save(
                                requestMapper.toParticipationRequest(user, event, Status.CONFIRMED)));

            }
        } else {
            throw new ServerException("Запрос не может быть добавлен организатором события или уже добавлен.");
        }
    }


    @Override
    @Transactional
    public ParticipationRequestDto setRequestStatus(Long userId, Long eventId, Long reqId, Status status) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ServerException("Пользователь с таким ID отсутствует."));
        ParticipationRequest request = requestRepository.findById(reqId).orElseThrow(
                () -> new ServerException("Запрос с таким ID отсутствует."));
        Event event = request.getEvent();
        if (eventId.equals(event.getId())) {
            if (status == Status.CONFIRMED && !request.getStatus().equals(Status.CONFIRMED)) {
                if (event.getConfirmedRequests() < event.getParticipantLimit()) {
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                    eventRepository.save(event);
                    request.setStatus(Status.CONFIRMED);
                    ParticipationRequest requestConf = requestRepository.save(request);
                    if (event.getConfirmedRequests().equals(event.getParticipantLimit())) {
                        List<ParticipationRequest> requestsToReject = requestRepository
                                .findParticipationRequestsByEventAndStatus(event, Status.PENDING);
                        for (ParticipationRequest requestPending : requestsToReject) {
                            requestPending.setStatus(Status.REJECTED);
                            requestRepository.save(requestPending);
                        }
                    }
                    return requestMapper.toDto(requestConf);
                }
            }
            if (status == Status.REJECTED) {
                request.setStatus(Status.REJECTED);
                return requestMapper.toDto(requestRepository.save(request));
            }

        } else {
            throw new ServerException("Значение eventId не соответствует ID в запросе.");
        }
        return null;
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long reqId) {
       ParticipationRequest request = requestRepository.findById(reqId).orElseThrow(
                () -> new ServerException("Запрос с таким ID отсутствует."));
        if (Objects.equals(userId, request.getRequester().getId())) {
            if (request.getStatus() == Status.CONFIRMED) {
                Event event = request.getEvent();
                event.setConfirmedRequests(event.getConfirmedRequests() > 0 ? (event.getConfirmedRequests() - 1) : 0);
                eventRepository.save(event);
            }
            request.setStatus(Status.CANCELED);
            return requestMapper.toDto(requestRepository.save(request));
        } else {
            throw new ServerException("Отменить можно только свой запрос.");
        }
    }
}
