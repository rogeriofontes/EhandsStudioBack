package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ProductDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.product.ProductCategory;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.domain.product.ProductTag;
import com.maosencantadas.model.service.ProductTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    @Autowired
    private ProductTagService productTagService;

    public ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setTechnicalData(product.getTechnicalData());
        dto.setSize(product.getSize());
        dto.setDiscount(product.getDiscount());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setArtistId(product.getArtist() != null ? product.getArtist().getPerson().getId() : null);
        dto.setProductCategoryId(product.getProductCategory() != null ? product.getProductCategory().getId() : null);
        dto.setMediaId(product.getMedia().getId() != null ? product.getMedia().getId() : null);

        // Adicionando tags ao DTO
        dto.setTagIds(product.getTags() != null
                ? product.getTags().stream().map(ProductTag::getId).collect(Collectors.toSet())
                : Collections.emptySet());

        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setTechnicalData(dto.getTechnicalData());
        product.setSize(dto.getSize());
        product.setDiscount(dto.getDiscount());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());
        product.setAcceptPersonalization(true);

        if (dto.getMediaId() != null) {
            Media media = new Media();
            media.setId(dto.getMediaId());
            product.setMedia(media);
        }

        if (dto.getArtistId() != null) {
            Artist artist = new Artist();
            artist.getPerson().setId(dto.getArtistId());
            product.setArtist(artist);
        }

        if (dto.getProductCategoryId() != null) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(dto.getProductCategoryId());
            product.setProductCategory(productCategory);
        }

        // Convertendo tagIds para entidades Tag
        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            List<ProductTag> allById = productTagService.findAllById(dto.getTagIds());
            Set<ProductTag> tags = new HashSet<>(allById);
            product.setTags(tags);
        } else {
            product.setTags(Collections.emptySet());
        }

        return product;
    }

    public List<ProductDTO> toDTO(List<Product> products) {
        if (products == null || products.isEmpty()) return Collections.emptyList();

        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Product> toEntity(List<ProductDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) return Collections.emptyList();

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}



