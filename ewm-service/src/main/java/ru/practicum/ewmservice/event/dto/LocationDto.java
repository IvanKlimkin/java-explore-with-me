package ru.practicum.ewmservice.event.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LocationDto {
    private Float lat;
    private Float lon;
}
