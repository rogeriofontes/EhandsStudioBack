package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ImageDTO;
import com.maosencantadas.model.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
@Tag(name = "Image API", description = "Endpoints for uploading and retrieving images")
public class ImageController {

    private final ImageService imageService;

    //TODO - Criara um media entity para armazenar as imagens no banco de dados e ser usado nos outros objetos
    @Operation(summary = "Upload an image")
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDTO> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "artist", required = false) Long artistId,
            @RequestParam(value = "product", required = false) Long productId,
            @RequestParam(value = "category", required = false) Long categoryId
    ) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String folder = "uploads";
            Path destination = Paths.get(folder, filename);

            Files.createDirectories(destination.getParent());
            file.transferTo(destination);

            ImageDTO dto = new ImageDTO();
            dto.setName(filename);
            dto.setFolder(folder);
            dto.setArtist(artistId);
            dto.setProduct(productId);
            dto.setCategory(categoryId);

            ImageDTO response = imageService.saveImage(dto);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            log.error("Failed to save image", e);
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Get image by file name")
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Path path = Paths.get("uploads").resolve(fileName);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Failed to serve image", e);
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Get images by artist ID")
    @GetMapping("/artist/{id}")
    public ResponseEntity<List<ImageDTO>> getImagesByArtist(@PathVariable Long id) {
        try {
            List<ImageDTO> images = imageService.findByArtistId(id);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            log.error("Failed to retrieve images by artist", e);
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Get images by product ID")
    @GetMapping("/product/{id}")
    public ResponseEntity<List<ImageDTO>> getImagesByProduct(@PathVariable Long id) {
        try {
            List<ImageDTO> images = imageService.findByProductId(id);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            log.error("Failed to retrieve images by product", e);
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Get images by category ID")
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ImageDTO>> getImagesByCategory(@PathVariable Long id) {
        try {
            List<ImageDTO> images = imageService.findByCategoryId(id);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            log.error("Failed to retrieve images by category", e);
            return ResponseEntity.status(500).build();
        }
    }
}
