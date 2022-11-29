package ru.practicum.ewmservice.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.request.model.Status;
import ru.practicum.ewmservice.request.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getAllUserOwnRequests(@PathVariable Long userId) {
        return requestService.getAllUserOwnRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto add(@PathVariable Long userId, @RequestParam Long eventId) {
        return requestService.createNewRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getAllUserEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        return requestService.getAllUserEventRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{requestId}/confirm")
    public ParticipationRequestDto setRequestStatusConfirm(@PathVariable Long userId,
                                                           @PathVariable Long eventId,
                                                           @PathVariable Long requestId) {
        return requestService.setRequestStatus(userId, eventId, requestId, Status.CONFIRMED);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{requestId}/reject")
    public ParticipationRequestDto setRequestStatusReject(@PathVariable Long userId,
                                                          @PathVariable Long eventId,
                                                          @PathVariable Long requestId) {
        return requestService.setRequestStatus(userId, eventId, requestId, Status.REJECTED);
    }

}
