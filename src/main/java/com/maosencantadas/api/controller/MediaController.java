package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.MediaDTO;
import com.maosencantadas.model.service.MediaService;
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
@RequestMapping("/v1/medias")
@Tag(name = "Image API", description = "Endpoints for uploading and retrieving images")
public class MediaController {
    private final MediaService mediaService;

    @Operation(summary = "Upload an image")
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaDTO> uploadImage(
            @RequestParam("file") MultipartFile file) {
        MediaDTO response = mediaService.save(file);
        return ResponseEntity.ok(response);
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
}
