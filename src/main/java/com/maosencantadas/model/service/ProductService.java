package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ProductDTO;
import com.maosencantadas.model.domain.product.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findByCategory(Long categoryId);

    List<Product> findByArtist(Long artistId);

    Product findById(Long id);

    Product save(Product product);

    Product update(Long id, Product updatedProduct);

    void delete(Long id);
}
