package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.product.ProductAssessment;
import com.maosencantadas.model.domain.product.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductAssessmentService {

    List<ProductAssessment> findAll();

    ProductAssessment findById(Long id);

    ProductAssessment save(ProductAssessment productAssessment);

    ProductAssessment update(Long id, ProductAssessment productAssessment);

    void delete(Long id);

    Optional<ProductAssessment> findByName(String categoryName);

    boolean existsById(Long categoryId);
}
