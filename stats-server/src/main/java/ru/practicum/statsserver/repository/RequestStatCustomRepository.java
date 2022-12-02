package ru.practicum.statsserver.repository;

import com.querydsl.core.Tuple;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestStatCustomRepository {
    List<Tuple> findReq(LocalDateTime start, LocalDateTime end, List<String> uris);

    List<Tuple> findUniqReq(LocalDateTime start, LocalDateTime end, List<String> uris);

}
