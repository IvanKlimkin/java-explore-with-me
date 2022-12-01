package ru.practicum.ewmservice.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll(@PositiveOrZero @RequestParam(
            name = "from", defaultValue = "0") Integer from,
                                    @Positive @RequestParam(
                                            name = "size", defaultValue = "10") Integer size) {
        final EwmPageRequest pageRequest = new EwmPageRequest(from, size, Sort.unsorted());
        return categoryService.getAllCategories(pageRequest);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }
}
