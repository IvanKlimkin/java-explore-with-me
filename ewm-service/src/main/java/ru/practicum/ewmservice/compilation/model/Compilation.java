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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pinned", columnDefinition = "boolean default false", nullable = false)
    private Boolean pinned;
    @Column(name = "title", nullable = false, length = 512)
    private String title;
    @OneToMany(mappedBy = "compilation")
    private List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        events.add(event);
        event.setCompilation(this);
    }

    public void removeEvent(Event event) {
        events.remove(event);
        event.setCompilation(null);
    }
}
