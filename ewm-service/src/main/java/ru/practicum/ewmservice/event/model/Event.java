package ru.practicum.ewmservice.event.model;

import lombok.*;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "events")
public class Event {
    @Column(name = "annotation", nullable = false, length = 1024)
    private String annotation;

    @OneToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "confirmed_Requests", columnDefinition = "bigint default 0")
    private Integer confirmedRequests;

    @Column(name = "created_On", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Column(name = "event_date", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false)
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @OneToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "paid", columnDefinition = "boolean default false", nullable = false)
    private Boolean paid;

    @Column(name = "participant_Limit", columnDefinition = "bigint default 0", nullable = false)
    private Integer participantLimit;

    @Column(name = "published_On", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime publishedOn;

    @Column(name = "request_Moderation", columnDefinition = "boolean default true", nullable = false)
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "title", nullable = false, length = 512)
    private String title;

    @Column(name = "views", columnDefinition = "bigint default 0", nullable = false)
    private Integer views;
    @Column(name = "compilation_id")
    private Long compilationId;

}
