package ru.practicum.statsserver;

import com.querydsl.core.Tuple;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatMapper {
    private QRequestStat request = QRequestStat.requestStat;

    public RequestStat toRequest(RequestDto requestDto) {
        return new RequestStat(
                requestDto.getId(),
                requestDto.getApp(),
                requestDto.getUri(),
                requestDto.getIp(),
                LocalDateTime.parse(requestDto.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public RequestDto toDto(RequestStat requestStat) {
        return new RequestDto(
                requestStat.getId(),
                requestStat.getApp(),
                requestStat.getUri(),
                requestStat.getIp(),
                requestStat.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    public ViewStats toViewDto(Tuple tuple) {
        return new ViewStats(
                tuple.get(0, String.class),
                tuple.get(1, String.class),
                tuple.get(2, Long.class));
    }

    public List<ViewStats> toViewDto(List<Tuple> tuples) {
        List<ViewStats> dtos = new ArrayList<>();
        for (Tuple row : tuples) {
            dtos.add(toViewDto(row));
        }
        return dtos;
    }
}