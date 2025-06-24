package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.MediaDTO;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.media.MediaType;
import com.maosencantadas.model.repository.MediaRepository;
import com.maosencantadas.model.service.MediaService;
import com.maosencantadas.utils.MediaUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    @Value("${app.upload.dir}")
    private String uploadSODirectory;

    private final MediaRepository mediaRepository;

    @Override
    public MediaDTO save(MultipartFile file) {

        try {
            String originalFilename = file.getOriginalFilename();
            String filename = UUID.randomUUID() + "_" + originalFilename;
            Path uploadDir = Paths.get(uploadSODirectory);

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String folder = "uploads";
            Path destination = Paths.get(folder, filename);
            String absolutePath = uploadDir.resolve(destination).toString();

            Files.createDirectories(destination.getParent());
            file.transferTo(destination);

            // Determina o tipo de mídia baseado na extensão
            String fileExtension = MediaUtil.getFileExtension(originalFilename);
            MediaType type = MediaUtil.determineMediaType(fileExtension);

            if (type == null) {
                log.error("Unsupported media type for file: {}", originalFilename);
                throw new IllegalArgumentException("Unsupported media type");
            }

            // Salva a imagem no banco de dados
            log.info("Saving media file: {}", filename);
            log.info("File saved at: {}", absolutePath);
            log.info("Media type: {}", type);

            Media dto = Media.builder()
                    .name(filename)
                    .folder(absolutePath)
                    .type(type)
                    .build();

            mediaRepository.save(dto);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Media findById(Long mediaId) {
        log.info("Creating user for artist with media ID: {}", mediaId);

        Optional<Media> media = mediaRepository.findById(mediaId);

        if (media.isPresent()) {
            log.debug("Media found with ID: {}", media.get().getId());
            return media.get();
        } else {
            log.warn("Media not found with ID: {}", media);
            throw new EntityNotFoundException("User not found with ID: " + media);
        }
    }
}
