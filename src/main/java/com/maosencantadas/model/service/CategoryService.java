package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.category.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    Category update(Long id, Category category);

    void delete(Long id);

    Optional<Category> findByName(String categoryName);

    boolean existsById(Long categoryId);
}
