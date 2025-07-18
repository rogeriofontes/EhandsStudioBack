package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ProductTagDTO;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.product.ProductTag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductTagMapper {

    private final ModelMapper modelMapper;

    public ProductTagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Tag → TagDTO
        TypeMap<ProductTag, ProductTagDTO> toDTOTypeMap = modelMapper.createTypeMap(ProductTag.class, ProductTagDTO.class);
        toDTOTypeMap.setPostConverter(context -> {
            ProductTag source = context.getSource();
            ProductTagDTO destination = context.getDestination();

            // Map fields manually if needed
            destination.setId(source.getId());
            destination.setName(source.getName());

            return destination;
        });

        // TagDTO → Tag
        TypeMap<ProductTagDTO, ProductTag> toEntityTypeMap = modelMapper.createTypeMap(ProductTagDTO.class, ProductTag.class);
        toEntityTypeMap.setPostConverter(context -> {
            ProductTagDTO source = context.getSource();
            ProductTag destination = context.getDestination();

            // Map fields manually if needed
            destination.setId(source.getId());
            destination.setName(source.getName());

            return destination;
        });
    }

    public ProductTagDTO toDTO(ProductTag productTag) {
        return modelMapper.map(productTag, ProductTagDTO.class);
    }

    public ProductTag toEntity(ProductTagDTO productTagDTO) {
        return modelMapper.map(productTagDTO, ProductTag.class);
    }

    public List<ProductTagDTO> toDTO(List<ProductTag> categories) {
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
