package com.maosencantadas.api.mapper;

import com.maosencantadas.model.domain.media.Media;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.maosencantadas.api.dto.CategoryDTO;
import com.maosencantadas.model.domain.category.Category;

@Component
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Category → CategoryDTO
        TypeMap<Category, CategoryDTO> toDTOTypeMap = modelMapper.createTypeMap(Category.class, CategoryDTO.class);
        toDTOTypeMap.setPostConverter(context -> {
            Category source = context.getSource();
            CategoryDTO destination = context.getDestination();

            if (source.getMedia() != null) {
                destination.setMediaId(source.getMedia().getId());
            }

            return destination;
        });

        // CategoryDTO → Category
        TypeMap<CategoryDTO, Category> toEntityTypeMap = modelMapper.createTypeMap(CategoryDTO.class, Category.class);
        toEntityTypeMap.setPostConverter(context -> {
            CategoryDTO source = context.getSource();
            Category destination = context.getDestination();

            if (source.getMediaId() != null) {
                Media media = new Media();
                media.setId(source.getMediaId());
                destination.setMedia(media);
            }

            return destination;
        });
    }

    public CategoryDTO toDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
