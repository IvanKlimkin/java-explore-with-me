package ru.practicum.ewmservice.event.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TwoHourDelayValidator implements ConstraintValidator<TwoHourDelay, String> {


    @Override
    public void initialize(TwoHourDelay twoHourDelay) {

    }

    @Override
    public boolean isValid(String eventDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (LocalDateTime.parse(eventDateTime, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"))
                .isBefore(LocalDateTime.now().plusHours(2))) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                            "Время начала мероприятия не раньше чем через 2 часа")
                    .addPropertyNode("Start event failure").addConstraintViolation();
            return false;
        }
        return true;
    }
}
