package com.maosencantadas.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.model.domain.artist.Artist;

@Component
public class ArtistMapper {

    private final ModelMapper modelMapper;

    public ArtistMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.configureMappings();
    }

    private void configureMappings() {
        modelMapper.typeMap(ArtistDTO.class, Artist.class)
                .addMappings(map -> map.skip(Artist::setCategory));

        modelMapper.typeMap(Artist.class, ArtistDTO.class)
                .addMapping(src -> src.getCategory().getId(),
                        (dest, value) -> dest.setCategory((Long) value));
    }

    public ArtistDTO toDTO(Artist artist) {
        return modelMapper.map(artist, ArtistDTO.class);
    }

    public Artist toEntity(ArtistDTO artistDTO) {
        return modelMapper.map(artistDTO, Artist.class);
    }
}

