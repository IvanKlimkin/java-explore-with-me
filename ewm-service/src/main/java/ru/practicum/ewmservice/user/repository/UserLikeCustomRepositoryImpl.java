package ru.practicum.ewmservice.user.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.user.model.QUserLike;
import ru.practicum.ewmservice.user.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class UserLikeCustomRepositoryImpl implements UserLikeCustomRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Tuple getUserRatedInfo(User user) {
        return new JPAQuery<>(em)
                .select(QUserLike.userLike.likeValue.sum(), QUserLike.userLike.id.count())
                .from(QUserLike.userLike)
                .where(QUserLike.userLike.ratedUser.id.eq(user.getId()))
                .fetchOne();
    }
}