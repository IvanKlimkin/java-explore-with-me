package ru.practicum.statsserver.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.statsserver.model.QRequestStat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RequestStatCustomRepositoryImpl implements RequestStatCustomRepository {
    @PersistenceContext
    private final EntityManager em;

    private QRequestStat request = QRequestStat.requestStat;

    @Override
    public List<Tuple> findReq(LocalDateTime start, LocalDateTime end, List<String> uris) {
        if (uris.isEmpty()) {
            return new JPAQuery<>(em)
                    .select(request.app, request.uri, request.id.count())
                    .from(request)
                    .where(request.timestamp.between(start, end))
                    .groupBy(request.app, request.uri)
                    .fetch();
        } else {
            return new JPAQuery<>(em)
                    .select(request.app, request.uri, request.id.count())
                    .from(request)
                    .where(request.timestamp.between(start, end).and(request.uri.in(uris)))
                    .groupBy(request.app, request.uri)
                    .fetch();
        }
    }

    @Override
    public List<Tuple> findUniqReq(LocalDateTime start, LocalDateTime end, List<String> uris) {
        if (uris.isEmpty()) {
            return new JPAQuery<>(em)
                    .select(request.app, request.uri, request.id.count(), request.id)
                    .distinct()
                    .from(request)
                    .where(request.timestamp.between(start, end))
                    .groupBy(request.app, request.uri)
                    .fetch();
        } else {
            return new JPAQuery<>(em)
                    .select(request.app, request.uri, request.id.count(), request.id)
                    .distinct()
                    .from(request)
                    .where(request.timestamp.between(start, end).and(request.uri.in(uris)))
                    .groupBy(request.app, request.uri)
                    .fetch();
        }
    }
}
