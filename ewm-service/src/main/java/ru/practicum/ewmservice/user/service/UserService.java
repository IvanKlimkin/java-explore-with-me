package ru.practicum.ewmservice.user.service;

import ru.practicum.ewmservice.EwmPageRequest;
import ru.practicum.ewmservice.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsersInList(List<Long> ids, EwmPageRequest pageRequest);

    UserDto createNewUser(UserDto userDto);

    UserDto getUserById(Long userId);

    void deleteUser(Long userId);

}
