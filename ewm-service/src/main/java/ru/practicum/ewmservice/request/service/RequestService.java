package ru.practicum.ewmservice.request.service;

import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.model.Status;

import java.util.List;

public interface RequestService {

    List<ParticipationRequestDto> getAllUserOwnRequests(Long userId);

    List<ParticipationRequestDto> getAllUserEventRequests(Long userId, Long eventId);

    ParticipationRequestDto createNewRequest(Long userId, Long eventId);

    ParticipationRequestDto setRequestStatus(Long userId, Long eventId, Long reqId, Status status);

    ParticipationRequestDto cancelRequest(Long userId, Long reqId);

}
