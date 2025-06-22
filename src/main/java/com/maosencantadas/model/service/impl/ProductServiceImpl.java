package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.ProductDTO;
import com.maosencantadas.api.mapper.ProductMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.repository.ArtistRepository;
import com.maosencantadas.model.repository.CategoryRepository;
import com.maosencantadas.model.repository.ProductRepository;
import com.maosencantadas.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final ArtistRepository artistRepository;

    @Override
    public List<ProductDTO> findAllProducts() {
        log.info("Listing all products with artist and category");
        return productRepository.findAllWithArtistAndCategory()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findProductsByCategory(Long categoryId) {
        log.info("Finding products by category with id: {}", categoryId);
        List<Product> products = productRepository.findByCategoryIdWithArtist(categoryId);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for category with id " + categoryId);
        }
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findProductsByArtist(Long artistId) {
        log.info("Finding products by artist with id: {}", artistId);
        List<Product> products = productRepository.findByArtistIdWithCategory(artistId);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for artist with id " + artistId);
        }
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findProductById(Long id) {
        log.info("Finding product by id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return productMapper.toDTO(product);
    }


    @Override
    public ProductDTO saveProduct(ProductDTO dto) {
        log.info("Saving new product");

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + dto.getCategoryId()));

        Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with name: " + dto.getArtistId()));

        Product product = productMapper.toEntity(dto);
        product.setCategory(category);
        product.setArtist(artist);

        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        log.info("Updating product with id: {}", id);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + dto.getCategoryId()));

        Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with name: " + dto.getArtistId()));

        Product updated = productRepository.findById(id)
                .map(product -> {
                    product.setName(dto.getName());
                    product.setDescription(dto.getDescription());
                    product.setSize(dto.getSize());
                    product.setImageUrl(dto.getImageUrl());
                    product.setPrice(dto.getPrice());
                    product.setCategory(category);
                    product.setArtist(artist);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        return productMapper.toDTO(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }
        productRepository.deleteById(id);
    }
}
