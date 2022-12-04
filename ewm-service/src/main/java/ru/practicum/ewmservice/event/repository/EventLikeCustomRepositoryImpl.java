package ru.practicum.ewmservice.event.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.QEventLike;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class EventLikeCustomRepositoryImpl implements EventLikeCustomRepository {
    @PersistenceContext
    private final EntityManager em;

    //private QRequestStat request = QRequestStat.requestStat;

    @Override
    public Tuple getEventLikesInfo(Event event) {
        return new JPAQuery<>(em)
                .select(QEventLike.eventLike.likeValue.sum(), QEventLike.eventLike.id.count())
                .from(QEventLike.eventLike)
                .where(QEventLike.eventLike.event.id.eq(event.getId()))
                .fetchOne();
    }
}
