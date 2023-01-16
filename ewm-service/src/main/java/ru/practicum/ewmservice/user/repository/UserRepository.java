package ru.practicum.ewmservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.utils.EwmPageRequest;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByIdIn(List<Long> ids, EwmPageRequest pageRequest);
}
