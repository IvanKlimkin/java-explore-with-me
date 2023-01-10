package ru.practicum.ewmservice.event.model;

import lombok.*;
import ru.practicum.ewmservice.user.model.User;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "event_likes")
public class EventLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "liker_id", nullable = false)
    private User liker;
    @OneToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @Column(name = "like_value", columnDefinition = "Float", nullable = false)
    private Float likeValue;
}
