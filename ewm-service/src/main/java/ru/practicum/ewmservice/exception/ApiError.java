package ru.practicum.ewmservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private List<StackTraceElement> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    private String timestamp;
}
