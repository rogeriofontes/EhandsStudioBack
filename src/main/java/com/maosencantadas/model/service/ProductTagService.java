package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.product.ProductTag;

import java.util.List;
import java.util.Optional;

public interface ProductTagService {

    List<ProductTag> findAll();

    ProductTag findById(Long id);

    ProductTag save(ProductTag productTag);

    ProductTag update(Long id, ProductTag productTag);

    void delete(Long id);

    Optional<ProductTag> findByName(String categoryName);

    boolean existsById(Long categoryId);
}
