package ru.practicum.ewmservice.event.model;

import lombok.*;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @OneToOne(fetch = FetchType.LAZY)
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @OneToOne(fetch = FetchType.LAZY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Compilation compilation;
    @Column(name = "rating", columnDefinition = "float default 0", nullable = false)
    private Float rating;
    @Column(name = "version")
    @Version
    private int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
