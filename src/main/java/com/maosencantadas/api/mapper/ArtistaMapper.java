package com.maosencantadas.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.maosencantadas.api.dto.ArtistaDTO;
import com.maosencantadas.model.domain.artista.Artista;

@Component
public class ArtistaMapper {

    private final ModelMapper modelMapper;

    public ArtistaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //  de Artista para ArtistaDTO
    public ArtistaDTO toDTO(Artista artista) {
        return modelMapper.map(artista, ArtistaDTO.class);
    }

    //  de ArtistaDTO para Artista
    public Artista toEntity(ArtistaDTO artistaDTO) {
        return modelMapper.map(artistaDTO, Artista.class);
    }
}
