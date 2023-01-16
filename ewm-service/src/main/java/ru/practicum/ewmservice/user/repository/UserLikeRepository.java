package ru.practicum.ewmservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.model.UserLike;

public interface UserLikeRepository extends JpaRepository<UserLike, Long>, UserLikeCustomRepository {
    UserLike findByLikerAndRatedUser(User liker, User ratedUser);
}
