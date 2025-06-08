package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.CategoryDTO;
import com.maosencantadas.api.mapper.CategoryMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.repository.CategoryRepository;
import com.maosencantadas.model.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> findAllCategories() {
        log.info("Listing all categories");
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    @Override
    public CategoryDTO findCategoryById(Long id) {
        log.info("Finding category by id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        log.info("Saving new category: {}", categoryDTO.getName());
        Category category = categoryMapper.toEntity(categoryDTO);
        Category categorySave = categoryRepository.save(category);
        return categoryMapper.toDTO(categorySave);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        log.info("Updating category with id: {}", id);
        Category categoryUpdated = categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDTO.getName());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
        return categoryMapper.toDTO(categoryUpdated);
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting category with id: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
        categoryRepository.deleteById(id);
    }
}
