package ru.practicum.ewmservice.category.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.model.Category;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public Category toCategory(@NotNull CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName());
    }

    public CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName());
    }

    public List<CategoryDto> toDto(Iterable<Category> categories) {
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories) {
            dtos.add(toDto(category));
        }
        return dtos;
    }

}