package ru.practicum.ewmservice.user.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", unique = true, nullable = false, length = 512)
    private String email;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "rating", columnDefinition = "float default 0", nullable = false)
    private Float rating;
}
