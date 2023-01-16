package ru.practicum.ewmservice.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.service.CategoryService;
import ru.practicum.ewmservice.utils.Create;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto add(@Validated({Create.class}) @RequestBody CategoryDto categoryDto) {
        return categoryService.createNewCategory(categoryDto);
    }

    @PatchMapping
    public CategoryDto update(@Validated({Create.class}) @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteItem(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
    }

}
