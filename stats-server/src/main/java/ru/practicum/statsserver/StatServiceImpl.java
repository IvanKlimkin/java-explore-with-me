package ru.practicum.statsserver;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final StatMapper statMapper;

    @Override
    @Transactional
    public RequestDto saveRequest(RequestDto requestDto) {
        return statMapper.toDto(statRepository.save(statMapper.toRequest(requestDto)));
    }

    @Override
    public List<ViewStats> getRequests(LocalDateTime start,
                                       LocalDateTime end,
                                       List<String> uris,
                                       Boolean unique) {
        List<Tuple> reqAndHits = new ArrayList<>();

        if (uris != null) {
            if (unique) {
                reqAndHits = statRepository.findUniqReq(start, end, uris);
            } else {
                reqAndHits = statRepository.findReq(start, end, uris);
            }
        } else if (unique) {
            reqAndHits = statRepository.findUniqReq(start, end, Collections.emptyList());
        } else {
            reqAndHits = statRepository.findReq(start, end, Collections.emptyList());
        }
        return statMapper.toViewDto(reqAndHits);
    }
}
