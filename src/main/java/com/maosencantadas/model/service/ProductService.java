package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    List<ProductDTO> findProductsByCategory(Long categoryId);

    List<ProductDTO> findProductsByArtist(Long artistId);

    ProductDTO findProductById(Long id);

    ProductDTO saveProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO updatedProductDTO);

    void deleteProduct(Long id);
}
