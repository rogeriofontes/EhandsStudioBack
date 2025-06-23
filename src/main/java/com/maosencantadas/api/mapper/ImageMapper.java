package com.maosencantadas.api.mapper;

import com.maosencantadas.model.domain.media.Media;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.maosencantadas.api.dto.MediaDTO;

@Component
public class ImageMapper {

    private final ModelMapper modelMapper;

    public ImageMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public MediaDTO toDTO (Media media) {return modelMapper.map(media, MediaDTO.class);}

    public Media toEntity (MediaDTO imageDTO) {return modelMapper.map(imageDTO, Media.class);}
}
