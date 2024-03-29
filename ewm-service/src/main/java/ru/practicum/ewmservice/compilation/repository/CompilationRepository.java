package ru.practicum.ewmservice.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.utils.EwmPageRequest;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findCompilationsByPinned(Boolean pinned, EwmPageRequest pageRequest);
}
