package ru.practicum.ewmservice.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.exception.ServerException;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.mapper.UserMapper;
import ru.practicum.ewmservice.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsersInList(List<Long> ids, EwmPageRequest pageRequest) {
        log.info("Запрос получения списка пользователей сервиса.");
        return userMapper.toDto(userRepository.findUsersByIdIn(ids, pageRequest));
    }

    @Override
    @Transactional
    public UserDto createNewUser(UserDto userDto) {
        log.info("Запрос создания нового пользователя сервиса.");
        return userMapper.toDto(
                userRepository.save(
                        userMapper.toUser(userDto)));
    }

    @Override
    public UserDto getUserById(Long catId) {
        log.info("Запрос получения пользователя по Id.");
        return userMapper.toDto(userRepository.findById(
                catId).orElseThrow(() -> new ServerException("Пользователь с таким ID отсутствует.")));
    }


    @Override
    @Transactional
    public void deleteUser(Long userId) {
        log.info("Запрос удаления пользователя.");
        userRepository.deleteById(userId);
    }
}
