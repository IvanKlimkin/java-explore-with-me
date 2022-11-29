package ru.practicum.statsserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StatRepository extends JpaRepository<RequestStat, Long>, RequestStatCustomRepository, QuerydslPredicateExecutor<RequestStat> {

}
