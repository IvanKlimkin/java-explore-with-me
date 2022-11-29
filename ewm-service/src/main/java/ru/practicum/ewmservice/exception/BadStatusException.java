package ru.practicum.ewmservice.exception;


public class BadStatusException extends RuntimeException {
    public BadStatusException(String message) {
        super(message);
    }
}