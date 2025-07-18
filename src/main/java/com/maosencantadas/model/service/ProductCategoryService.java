package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.product.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {

    List<ProductCategory> findAll();

    ProductCategory findById(Long id);

    ProductCategory save(ProductCategory productCategory);

    ProductCategory update(Long id, ProductCategory productCategory);

    void delete(Long id);

    Optional<ProductCategory> findByName(String categoryName);

    boolean existsById(Long categoryId);
}
