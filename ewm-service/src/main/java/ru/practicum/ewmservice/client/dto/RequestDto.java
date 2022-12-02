package ru.practicum.ewmservice.client.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
