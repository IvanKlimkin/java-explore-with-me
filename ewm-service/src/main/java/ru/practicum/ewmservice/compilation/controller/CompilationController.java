package ru.practicum.ewmservice.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        return compilationService.getCompilationById(compId);
    }

    @GetMapping
    public List<CompilationDto> getCompilationByCondition(@RequestParam(required = false) Boolean pinned,
                                                          @PositiveOrZero @RequestParam(
                                                                  name = "from", defaultValue = "0") Integer from,
                                                          @Positive @RequestParam(
                                                                  name = "size", defaultValue = "10") Integer size) {
        final EwmPageRequest pageRequest = new EwmPageRequest(from, size, Sort.unsorted());
        return compilationService.getCompilationsByCondition(pinned, pageRequest);
    }
}
