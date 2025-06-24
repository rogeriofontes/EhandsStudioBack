package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.mapper.ProductMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.repository.ProductRepository;
import com.maosencantadas.model.service.ArtistService;
import com.maosencantadas.model.service.CategoryService;
import com.maosencantadas.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final ArtistService artistService;

    @Override
    public List<Product> findAll() {
        log.info("Listing all products with artist and category");
        return productRepository.findAllWithArtistAndCategory();
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        log.info("Finding products by category with id: {}", categoryId);
        return productRepository.findByCategoryIdWithArtist(categoryId);
    }

    @Override
    public List<Product> findByArtist(Long artistId) {
        log.info("Finding products by artist with id: {}", artistId);
        return productRepository.findByArtistIdWithCategory(artistId);
    }

    @Override
    public Product findById(Long id) {
        log.info("Finding product by id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    @Override
    public Product save(Product product) {
        log.info("Saving new product");
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        log.info("Updating product with id: {}", id);
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setSize(product.getSize());
                    existingProduct.setImageUrl(product.getImageUrl());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setArtist(product.getArtist());
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }
        productRepository.deleteById(id);
    }
}
