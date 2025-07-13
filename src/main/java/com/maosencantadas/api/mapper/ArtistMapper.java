package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.user.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistMapper {

    private final ModelMapper modelMapper;

    public ArtistMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        configureArtistToArtistDTO();
        configureArtistDTOToArtist();
    }

    private void configureArtistToArtistDTO() {
        TypeMap<Artist, ArtistDTO> typeMap = modelMapper.createTypeMap(Artist.class, ArtistDTO.class);
        typeMap.setPostConverter(context -> {
            Artist source = context.getSource();
            ArtistDTO destination = context.getDestination();

            if (source.getUser() != null) {
                destination.setUserId(source.getUser().getId());
            }

            if (source.getCategory() != null) {
                destination.setCategoryId(source.getCategory().getId());
            }

            if (source.getMedia() != null) {
                destination.setMediaId(source.getMedia().getId());
            }

            return destination;
        });
    }

    private void configureArtistDTOToArtist() {
        TypeMap<ArtistDTO, Artist> typeMapReverse = modelMapper.createTypeMap(ArtistDTO.class, Artist.class);
        typeMapReverse.setPostConverter(context -> {
            ArtistDTO source = context.getSource();
            Artist destination = context.getDestination();

            if (source.getUserId() != null) {
                User user = new User();
                user.setId(source.getUserId());
                destination.setUser(user);
            }

            if (source.getCategoryId() != null) {
                Category category = new Category();
                category.setId(source.getCategoryId());
                destination.setCategory(category);
            }

            if (source.getMediaId() != null) {
                Media media = new Media();
                media.setId(source.getMediaId());
                destination.setMedia(media);
            }

            return destination;
        });
    }

    public ArtistDTO toDTO(Artist artist) {
        return modelMapper.map(artist, ArtistDTO.class);
    }

    public Artist toEntity(ArtistDTO artistDTO) {
        return modelMapper.map(artistDTO, Artist.class);
    }

    public List<Artist> toEntity(List<ArtistDTO> artistDTOs) {
        return artistDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<ArtistDTO> toDTO(List<Artist> artists) {
        return artists.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}