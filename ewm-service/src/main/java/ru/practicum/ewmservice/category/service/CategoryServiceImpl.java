package ru.practicum.ewmservice.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.repository.CategoryRepository;
import ru.practicum.ewmservice.exception.ServerException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategories(EwmPageRequest pageRequest) {
        log.info("Запрос получения всех категорий в сервисе.");
        return categoryMapper.toDto(categoryRepository.findAll(pageRequest));
    }

    @Override
    @Transactional
    public CategoryDto createNewCategory(CategoryDto categoryDto) {
        log.info("Запрос на создание новой категории.");
        return categoryMapper.toDto(
                categoryRepository.save(
                        categoryMapper.toCategory(categoryDto)));
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        log.info("Запрос получения категории по Id.");
        return categoryMapper.toDto(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ServerException("Категория с таким ID отсутствует.")));
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        log.info("Запрос на обновление категории.");
        return categoryMapper.toDto(
                categoryRepository.save(
                        categoryMapper.toCategory(categoryDto)));
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        log.info("Запрос на удаление категории.");
        categoryRepository.deleteById(categoryId);
    }
}
