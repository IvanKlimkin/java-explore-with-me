package ru.practicum.ewmservice.exception;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleServerException(ServerException e) {
        log.error(e.getMessage(), e);
        ApiError error = new ApiError(
                List.of(e.getStackTrace()),
                e.getMessage(),
                "Запрос к серверу не корректен.",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
        return error;
    }

    @ExceptionHandler(NotUniqException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConstraintViolationException(NotUniqException e) {
        log.error(e.getMessage(), e);
        ApiError error = new ApiError(
                List.of(e.getStackTrace()),
                e.getMessage(),
                "Наименование категории и электронная почта должны быть уникальны.",
                HttpStatus.CONFLICT,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
        return error;
    }

    @ExceptionHandler(PropertyValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlePropertyValueException(PropertyValueException e) {
        log.error(e.getMessage(), e);
        ApiError error = new ApiError(
                List.of(e.getStackTrace()),
                e.getMessage(),
                "Уловия запроса к серверу не выполнены.",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        ApiError error = new ApiError(
                List.of(e.getStackTrace()),
                e.getMessage(),
                "Уловия запроса к серверу не выполнены.",
                HttpStatus.CONFLICT,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
        return error;
    }

}