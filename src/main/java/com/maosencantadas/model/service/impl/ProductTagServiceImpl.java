package com.maosencantadas.model.service.impl;

import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.product.ProductCategory;
import com.maosencantadas.model.domain.product.ProductTag;
import com.maosencantadas.model.repository.ProductCategoryRepository;
import com.maosencantadas.model.repository.ProductTagRepository;
import com.maosencantadas.model.service.ProductCategoryService;
import com.maosencantadas.model.service.ProductTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductTagServiceImpl implements ProductTagService {

    private final ProductTagRepository productTagRepository;

    @Override
    public List<ProductTag> findAll() {
        log.info("Listing all categories");
        return productTagRepository.findAll();
    }

    @Override
    public ProductTag findById(Long id) {
        log.info("Finding category by id: {}", id);
        return productTagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public ProductTag save(ProductTag productTag) {
        log.info("Saving new category: {}", productTag.getName());
        return productTagRepository.save(productTag);
    }

    @Override
    public ProductTag update(Long id, ProductTag productTag) {
        log.info("Updating category with id: {}", id);
        return productTagRepository.findById(id)
                .map(categoryR -> {
                    categoryR.setName(productTag.getName());
                    return productTagRepository.save(productTag);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category with id: {}", id);
        if (!productTagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
        productTagRepository.deleteById(id);
    }

    @Override
    public Optional<ProductTag> findByName(String categoryName) {
        return productTagRepository.findByName(categoryName);
    }

    @Override
    public boolean existsById(Long productCategoryId) {
        return productTagRepository.existsById(productCategoryId);
    }
}
