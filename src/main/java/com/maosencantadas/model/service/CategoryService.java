package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAllCategories();

    CategoryDTO findCategoryById(Long id);

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
