package ru.practicum.ewmservice.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getName(),
                0F);
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRating());
    }

    public List<UserDto> toDto(Iterable<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(toDto(user));
        }
        return dtos;
    }

}