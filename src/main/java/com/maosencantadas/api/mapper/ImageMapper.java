package com.maosencantadas.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.maosencantadas.api.dto.ImageDTO;
import com.maosencantadas.model.domain.image.Image;

@Component
public class ImageMapper {

    private final ModelMapper modelMapper;

    public ImageMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public ImageDTO toDTO (Image image) {return modelMapper.map(image , ImageDTO.class);}

    public Image toEntity (ImageDTO imageDTO) {return modelMapper.map(imageDTO, Image.class);}
}
