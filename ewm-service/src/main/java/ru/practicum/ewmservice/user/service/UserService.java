package ru.practicum.ewmservice.user.service;

import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.utils.EwmPageRequest;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsersInList(List<Long> ids, EwmPageRequest pageRequest);

    UserDto createNewUser(UserDto userDto);

    UserDto getUserById(Long userId);

    void deleteUser(Long userId);

    UserDto rateUser(Long userId, Long ratedId, Float rateValue);

    UserDto updateRateUser(Long userId, Long ratedId, Float rateValue);

    void deleteRate(Long userId, Long ratedId);

}
