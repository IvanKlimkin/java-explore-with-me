package ru.practicum.ewmservice.event.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "latitude", columnDefinition = "float", nullable = false)
    private Float lat;
    @Column(name = "longitude", columnDefinition = "float", nullable = false)
    private Float lon;
}
