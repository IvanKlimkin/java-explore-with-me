package ru.practicum.ewmservice.category.service;

import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories(EwmPageRequest pageRequest);

    CategoryDto createNewCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(Long categoryId);

}
