package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.ImageDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.image.Image;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.repository.ArtistRepository;
import com.maosencantadas.model.repository.ImageRepository;
import com.maosencantadas.model.repository.ProductRepository;
import com.maosencantadas.model.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ArtistRepository artistRepository;
    private final ProductRepository productRepository;

    @Override
    public ImageDTO saveImage(ImageDTO imageDTO, MultipartFile file) {
        // Not implemented yet
        return null;
    }

    @Override
    public ImageDTO saveImage(ImageDTO dto) {
        Image image = new Image();
        image.setName(dto.getName());
        image.setFolder(dto.getFolder());

        if (dto.getArtist() != null) {
            Artist artist = artistRepository.findById(dto.getArtist())
                    .orElseThrow(() -> new RuntimeException("Artist not found"));
            image.setArtist(artist);

            String path = dto.getFolder() + "/" + dto.getName();
            artist.setImageUrl(path);
            artistRepository.save(artist);
        }

        if (dto.getProduct() != null) {
            Product product = productRepository.findById(dto.getProduct())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            image.setProduct(product);

            String path = dto.getFolder() + "/" + dto.getName();
            product.setImageUrl(path);
            productRepository.save(product);
        }

        Image savedImage = imageRepository.save(image);

        ImageDTO response = new ImageDTO();
        response.setId(savedImage.getId());
        response.setName(savedImage.getName());
        response.setFolder(savedImage.getFolder());
        response.setArtist(dto.getArtist());
        response.setProduct(dto.getProduct());

        return response;
    }

    @Override
    public List<ImageDTO> findByArtistId(Long artist_id) {
        return imageRepository.findByArtistId(artist_id).stream()
                .map(image -> {
                    ImageDTO dto = new ImageDTO();
                    dto.setId(image.getId());
                    dto.setName(image.getName());
                    dto.setFolder(image.getFolder());
                    dto.setArtist(artist_id);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ImageDTO> findByProductId(Long product_id) {
        return imageRepository.findByProductId(product_id).stream()
                .map(image -> {
                    ImageDTO dto = new ImageDTO();
                    dto.setId(image.getId());
                    dto.setName(image.getName());
                    dto.setFolder(image.getFolder());
                    dto.setProduct(product_id);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ImageDTO> findByCategoryId(Long category_id) {
        return imageRepository.findByCategoryId(category_id).stream()
                .map(image -> {
                    ImageDTO dto = new ImageDTO();
                    dto.setId(image.getId());
                    dto.setName(image.getName());
                    dto.setFolder(image.getFolder());
                    dto.setCategory(category_id);
                    return dto;
                }).collect(Collectors.toList());
    }
}
