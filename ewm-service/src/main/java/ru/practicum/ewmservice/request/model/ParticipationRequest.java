package ru.practicum.ewmservice.request.model;

import lombok.*;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "requests")
public class ParticipationRequest {
    @Column(name = "created", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false)
    private LocalDateTime created;
    @OneToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Enumerated(EnumType.STRING)
    private Status status;

}
