package ru.practicum.statsserver;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "requests")
public class RequestStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "application_name", nullable = false, length = 128)
    private String app;
    @Column(name = "request_uri", nullable = false, length = 128)
    private String uri;
    @Column(name = "ip_address", nullable = false, length = 128)
    private String ip;
    @Column(name = "time_requested", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime timestamp;
}
