package ru.practicum.ewmservice.user.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_likes")
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "liker_id", nullable = false)
    private User liker;
    @OneToOne
    @JoinColumn(name = "rated_id", nullable = false)
    private User ratedUser;
    @Column(name = "like_value", columnDefinition = "Float", nullable = false)
    private Float likeValue;
}