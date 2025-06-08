package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract interface ImageService {

    ImageDTO saveImage(ImageDTO imageDTO, MultipartFile file);

    ImageDTO saveImage(ImageDTO dto);

    List<ImageDTO> findByArtistId(Long artistId);

    List<ImageDTO> findByProductId(Long productId);

    List<ImageDTO> findByCategoryId(Long categoryId);
}
