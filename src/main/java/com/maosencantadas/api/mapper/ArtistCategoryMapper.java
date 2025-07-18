package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ArtistCategoryDTO;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.artist.ArtistCategory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistCategoryMapper {

    private final ModelMapper modelMapper;

    public ArtistCategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Category → CategoryDTO
        TypeMap<ArtistCategory, ArtistCategoryDTO> toDTOTypeMap = modelMapper.createTypeMap(ArtistCategory.class, ArtistCategoryDTO.class);
        toDTOTypeMap.setPostConverter(context -> {
            ArtistCategory source = context.getSource();
            ArtistCategoryDTO destination = context.getDestination();

            if (source.getMedia() != null) {
                destination.setMediaId(source.getMedia().getId());
            }

            return destination;
        });

        // CategoryDTO → Category
        TypeMap<ArtistCategoryDTO, ArtistCategory> toEntityTypeMap = modelMapper.createTypeMap(ArtistCategoryDTO.class, ArtistCategory.class);
        toEntityTypeMap.setPostConverter(context -> {
            ArtistCategoryDTO source = context.getSource();
            ArtistCategory destination = context.getDestination();

            if (source.getMediaId() != null) {
                Media media = new Media();
                media.setId(source.getMediaId());
                destination.setMedia(media);
            }

            return destination;
        });
    }

    public ArtistCategoryDTO toDTO(ArtistCategory artistCategory) {
        return modelMapper.map(artistCategory, ArtistCategoryDTO.class);
    }

    public ArtistCategory toEntity(ArtistCategoryDTO artistCategoryDTO) {
        return modelMapper.map(artistCategoryDTO, ArtistCategory.class);
    }

    public List<ArtistCategoryDTO> toDTO(List<ArtistCategory> categories) {
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
