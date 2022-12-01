package ru.practicum.statsserver.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statsserver.dto.ViewStats;
import ru.practicum.statsserver.dto.RequestDto;
import ru.practicum.statsserver.mapper.StatMapper;
import ru.practicum.statsserver.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final StatMapper statMapper;

    @Override
    @Transactional
    public RequestDto saveRequest(RequestDto requestDto) {
        log.info("Сохранен запрос на определенный url в сервисе статистики.");
        return statMapper.toDto(statRepository.save(statMapper.toRequest(requestDto)));
    }

    @Override
    public List<ViewStats> getRequests(LocalDateTime start,
                                       LocalDateTime end,
                                       List<String> uris,
                                       Boolean unique) {
        List<Tuple> reqAndHits = new ArrayList<>();
        log.info("Запрос получение статистики из сервиса Статистики.");
        if (uris != null) {
            reqAndHits = unique ? statRepository.findUniqReq(start, end, uris)
                    : statRepository.findReq(start, end, uris);
        } else if (unique) {
            reqAndHits = statRepository.findUniqReq(start, end, Collections.emptyList());
        } else {
            reqAndHits = statRepository.findReq(start, end, Collections.emptyList());
        }
        return statMapper.toViewDto(reqAndHits);
    }
}
