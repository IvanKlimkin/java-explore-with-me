package ru.practicum.ewmservice.request.dto;

import lombok.*;
import ru.practicum.ewmservice.request.model.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ParticipationRequestDto {
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private Status status;
}
