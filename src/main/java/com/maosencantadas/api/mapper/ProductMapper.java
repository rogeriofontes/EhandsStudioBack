package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ProductDTO;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setSize(product.getSize());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setArtistId(product.getArtist() != null ? product.getArtist().getId() : null);
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setMediaId(product.getMedia().getId() != null ? product.getMedia().getId() : null);

        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setSize(dto.getSize());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());

        if (dto.getMediaId() != null) {
            Media media = new Media();
            media.setId(dto.getMediaId());
            product.setMedia(media);
        }

        return product;
    }
}



