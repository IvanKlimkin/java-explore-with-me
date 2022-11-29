package ru.practicum.ewmservice.compilation.model;

import lombok.*;
import ru.practicum.ewmservice.event.model.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "compilations")
public class Compilation {
    @OneToMany
    @JoinColumn(name = "compilation_id")
    List<Event> events = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "pinned", columnDefinition = "boolean default false", nullable = false)
    Boolean pinned;
    @Column(name = "title", nullable = false, length = 512)
    String title;
}
