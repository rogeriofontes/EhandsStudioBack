package com.maosencantadas.model.service.impl;

import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.product.ProductCategory;
import com.maosencantadas.model.repository.ProductCategoryRepository;
import com.maosencantadas.model.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findAll() {
        log.info("Listing all categories");
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory findById(Long id) {
        log.info("Finding category by id: {}", id);
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        log.info("Saving new category: {}", productCategory.getName());
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory update(Long id, ProductCategory productCategory) {
        log.info("Updating category with id: {}", id);
        return productCategoryRepository.findById(id)
                .map(categoryR -> {
                    categoryR.setName(productCategory.getName());
                    return productCategoryRepository.save(productCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category with id: {}", id);
        if (!productCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
        productCategoryRepository.deleteById(id);
    }

    @Override
    public Optional<ProductCategory> findByName(String categoryName) {
        return productCategoryRepository.findByName(categoryName);
    }

    @Override
    public boolean existsById(Long productCategoryId) {
        return productCategoryRepository.existsById(productCategoryId);
    }
}
