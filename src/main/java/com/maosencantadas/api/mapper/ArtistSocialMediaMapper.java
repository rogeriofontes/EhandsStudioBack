package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ArtistSocialMediaDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.artist.ArtistSocialMedia;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistSocialMediaMapper {

    private final ModelMapper modelMapper;

    public ArtistSocialMediaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        configureArtistSocialMediaToArtistSocialMediaDTO();
        configureArtistSocialMediaDTOToArtistSocialMedia();
    }

    private void configureArtistSocialMediaToArtistSocialMediaDTO() {
        TypeMap<ArtistSocialMedia, ArtistSocialMediaDTO> typeMap = modelMapper.createTypeMap(ArtistSocialMedia.class, ArtistSocialMediaDTO.class);
        typeMap.setPostConverter(context -> {
            ArtistSocialMedia source = context.getSource();
            ArtistSocialMediaDTO destination = context.getDestination();

            if (source.getArtist() != null && source.getArtist().getId() != null) {
                destination.setArtistId(source.getArtist().getId());
            } else {
                destination.setArtistId(null);
            }

            return destination;
        });
    }

    private void configureArtistSocialMediaDTOToArtistSocialMedia() {
        TypeMap<ArtistSocialMediaDTO, ArtistSocialMedia> typeMapReverse = modelMapper.createTypeMap(ArtistSocialMediaDTO.class, ArtistSocialMedia.class);
        typeMapReverse.setPostConverter(context -> {
            ArtistSocialMediaDTO source = context.getSource();
            ArtistSocialMedia destination = context.getDestination();

            if (source.getArtistId() != null) {
                Artist artist = new Artist();
                artist.setId(source.getArtistId());
                destination.setArtist(artist);
            }

            return destination;
        });
    }

    public ArtistSocialMediaDTO toDTO(ArtistSocialMedia artistSocialMedia) {
        return modelMapper.map(artistSocialMedia, ArtistSocialMediaDTO.class);
    }

    public ArtistSocialMedia toEntity(ArtistSocialMediaDTO artistSocialMediaDTO) {
        return modelMapper.map(artistSocialMediaDTO, ArtistSocialMedia.class);
    }

    public List<ArtistSocialMedia> toEntity(List<ArtistSocialMediaDTO> artistSocialMediaDTOs) {
        return artistSocialMediaDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<ArtistSocialMediaDTO> toDTO(List<ArtistSocialMedia> artistSocialMedias) {
        return artistSocialMedias.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
