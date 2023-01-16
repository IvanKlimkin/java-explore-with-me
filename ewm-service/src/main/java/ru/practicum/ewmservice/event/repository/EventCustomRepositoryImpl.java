package ru.practicum.ewmservice.event.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.category.model.QCategory;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.QEvent;
import ru.practicum.ewmservice.event.model.QLocation;
import ru.practicum.ewmservice.user.model.QUser;
import ru.practicum.ewmservice.utils.EwmPageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventCustomRepositoryImpl implements EventCustomRepository {

    @PersistenceContext
    private final EntityManager em;

    QEvent event = QEvent.event;
    QUser user = QUser.user;
    QCategory category = QCategory.category;
    QLocation location = QLocation.location;

    @Override
    public Page<Event> searchEvents(BooleanExpression paramsPredicate, EwmPageRequest pageRequest) {
        List<Event> searchedEvents = new JPAQueryFactory(em).selectFrom(event)
                .innerJoin(event.initiator, user)
                .fetchJoin()
                .innerJoin(event.category, category)
                .fetchJoin()
                .innerJoin(event.location, location)
                .fetchJoin()
                .where(paramsPredicate)
                .fetch();

        int start = (int) pageRequest.getOffset();
        int end = (int) ((start + pageRequest.getPageSize()) > searchedEvents.size() ? searchedEvents.size()
                : (start + pageRequest.getPageSize()));

        return new PageImpl<Event>(searchedEvents.subList(start, end), pageRequest, searchedEvents.size());
    }

    @Override
    public Page<Event> getFilteredEvents(BooleanExpression condition, EwmPageRequest pageRequest) {
        List<Event> filteredEvents = new JPAQueryFactory(em).selectFrom(event)
                .innerJoin(event.initiator, user)
                .fetchJoin()
                .innerJoin(event.category, category)
                .fetchJoin()
                .innerJoin(event.location, location)
                .fetchJoin()
                .where(condition)
                .fetch();

        int start = (int) pageRequest.getOffset();
        int end = (int) ((start + pageRequest.getPageSize()) > filteredEvents.size() ? filteredEvents.size()
                : (start + pageRequest.getPageSize()));

        return new PageImpl<Event>(filteredEvents.subList(start, end), pageRequest, filteredEvents.size());
    }
}
