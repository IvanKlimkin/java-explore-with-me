package ru.practicum.statsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.statsserver.model.RequestStat;

public interface StatRepository extends JpaRepository<RequestStat, Long>, RequestStatCustomRepository, QuerydslPredicateExecutor<RequestStat> {

}
