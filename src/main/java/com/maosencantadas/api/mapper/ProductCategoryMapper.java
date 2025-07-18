package com.maosencantadas.api.mapper;

import com.maosencantadas.model.domain.product.ProductCategory;
import com.maosencantadas.model.domain.media.Media;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.maosencantadas.api.dto.ProductCategoryDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductCategoryMapper {

    private final ModelMapper modelMapper;

    public ProductCategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Category → CategoryDTO
        TypeMap<ProductCategory, ProductCategoryDTO> toDTOTypeMap = modelMapper.createTypeMap(ProductCategory.class, ProductCategoryDTO.class);
        toDTOTypeMap.setPostConverter(context -> {
            ProductCategory source = context.getSource();
            ProductCategoryDTO destination = context.getDestination();

            if (source.getMedia() != null) {
                destination.setMediaId(source.getMedia().getId());
            }

            return destination;
        });

        // CategoryDTO → Category
        TypeMap<ProductCategoryDTO, ProductCategory> toEntityTypeMap = modelMapper.createTypeMap(ProductCategoryDTO.class, ProductCategory.class);
        toEntityTypeMap.setPostConverter(context -> {
            ProductCategoryDTO source = context.getSource();
            ProductCategory destination = context.getDestination();

            if (source.getMediaId() != null) {
                Media media = new Media();
                media.setId(source.getMediaId());
                destination.setMedia(media);
            }

            return destination;
        });
    }

    public ProductCategoryDTO toDTO(ProductCategory productCategory) {
        return modelMapper.map(productCategory, ProductCategoryDTO.class);
    }

    public ProductCategory toEntity(ProductCategoryDTO productCategoryDTO) {
        return modelMapper.map(productCategoryDTO, ProductCategory.class);
    }

    public List<ProductCategoryDTO> toDTO(List<ProductCategory> categories) {
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
