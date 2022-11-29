package ru.practicum.ewmservice.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.model.ParticipationRequest;
import ru.practicum.ewmservice.request.model.Status;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {

    public ParticipationRequest toParticipationRequest(User requester, Event event, Status status) {
        return new ParticipationRequest(
                LocalDateTime.now(),
                event,
                0L,
                requester,
                status);
    }

    public ParticipationRequestDto toDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(
                participationRequest.getCreated().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")),
                participationRequest.getEvent().getId(),
                participationRequest.getId(),
                participationRequest.getRequester().getId(),
                participationRequest.getStatus());
    }

    public List<ParticipationRequestDto> toDto(Iterable<ParticipationRequest> requests) {
        List<ParticipationRequestDto> dtos = new ArrayList<>();
        for (ParticipationRequest participationRequest : requests) {
            dtos.add(toDto(participationRequest));
        }
        return dtos;
    }

}
