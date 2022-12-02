package ru.practicum.ewmservice.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class NotUniqException extends DataIntegrityViolationException {
    public NotUniqException(String message) {
        super(message);
    }
}