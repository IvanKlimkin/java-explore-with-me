package ru.practicum.ewmservice.user.repository;

import com.querydsl.core.Tuple;
import ru.practicum.ewmservice.user.model.User;

public interface UserLikeCustomRepository {
    Tuple getUserRatedInfo(User user);
}
