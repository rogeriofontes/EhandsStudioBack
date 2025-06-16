package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ProductDTO;
import com.maosencantadas.model.domain.product.Product;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.typeMap(Product.class, ProductDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getArtist().getName(), ProductDTO::setArtist);
                    mapper.map(src -> src.getCategory().getName(), ProductDTO::setCategory);
                });

        modelMapper.typeMap(ProductDTO.class, Product.class)
                .addMappings(mapper -> {
                    mapper.skip(Product::setArtist);
                    mapper.skip(Product::setCategory);
                });
    }

    public ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product toEntity(ProductDTO dto) {
        return modelMapper.map(dto, Product.class);
    }
}

