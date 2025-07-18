package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ArtistSocialMediaDTO;
import com.maosencantadas.api.mapper.ArtistSocialMediaMapper;
import com.maosencantadas.model.domain.artist.ArtistSocialMedia;
import com.maosencantadas.model.service.ArtistSocialMediaService;
import com.maosencantadas.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/artist-social-medias")
@CrossOrigin(origins = "*")
@Tag(name = "ArtistSocialMediaService", description = "Operations related to ArtistSocialMediaServices")
public class ArtistSocialMediaController {

    private final ArtistSocialMediaService artistSocialMediaService;
    private final ArtistSocialMediaMapper artistSocialMediaMapper;

    @GetMapping
    @Operation(summary = "List all artistSocialMediaServices", description = "Returns a list of all registered artistSocialMediaServices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ArtistSocialMediaServices successfully listed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistSocialMediaDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ArtistSocialMediaDTO>> findAll() {
        log.info("Listing all artistSocialMediaServices");
        List<ArtistSocialMedia> artistSocialMedias = artistSocialMediaService.findAll();
        List<ArtistSocialMediaDTO> artistSocialMediaServiceDTOS = artistSocialMediaMapper.toDTO(artistSocialMedias);
        return ResponseEntity.ok(artistSocialMediaServiceDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find artistSocialMediaService by ID", description = "Retrieves a single artistSocialMediaService by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ArtistSocialMediaService found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistSocialMediaDTO.class))),
            @ApiResponse(responseCode = "404", description = "ArtistSocialMediaService not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistSocialMediaDTO> findById(
            @Parameter(description = "Unique ID of the artistSocialMediaService", example = "1")
            @PathVariable Long id) {
        log.info("Finding artistSocialMediaService by ID: {}", id);
        ArtistSocialMedia artistSocialMedia = artistSocialMediaService.findById(id);
        ArtistSocialMediaDTO artistSocialMediaServiceDTO = artistSocialMediaMapper.toDTO(artistSocialMedia);
        return ResponseEntity.of(Optional.ofNullable(artistSocialMediaServiceDTO));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Find artistSocialMediaService by ID", description = "Retrieves a single artistSocialMediaService by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ArtistSocialMediaService found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistSocialMediaDTO.class))),
            @ApiResponse(responseCode = "404", description = "ArtistSocialMediaService not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistSocialMediaDTO> findByUserId(
            @Parameter(description = "Unique ID of the artistSocialMediaService", example = "1")
            @PathVariable("artistId") Long artistId) {
        log.info("Finding artistSocialMediaService by User ID: {}", artistId);
        ArtistSocialMedia artistSocialMedia = artistSocialMediaService.findByArtistId(artistId);
        ArtistSocialMediaDTO artistSocialMediaServiceDTO = artistSocialMediaMapper.toDTO(artistSocialMedia);
        return ResponseEntity.of(Optional.ofNullable(artistSocialMediaServiceDTO));
    }

    @PostMapping
    @Operation(summary = "Create a new artistSocialMediaService", description = "Creates and returns a new artistSocialMediaService with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ArtistSocialMediaService created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistSocialMediaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistSocialMediaDTO> create(
            @Schema(description = "New artistSocialMediaService data", requiredMode = Schema.RequiredMode.REQUIRED)
            @RequestBody ArtistSocialMediaDTO artistSocialMediaDTO) {
        log.info("Creating new artistSocialMediaService: {}", artistSocialMediaDTO.getArtistId());
        ArtistSocialMedia artistSocialMedia = artistSocialMediaMapper.toEntity(artistSocialMediaDTO);
        ArtistSocialMedia createdArtistSocialMedia = artistSocialMediaService.save(artistSocialMedia);
        log.info("Creating artistSocialMediaService: {}", createdArtistSocialMedia);

        ArtistSocialMediaDTO createdArtistSocialMediaDTO = artistSocialMediaMapper.toDTO(createdArtistSocialMedia);
        URI location = RestUtils.getUri(artistSocialMedia.getId());
        return ResponseEntity.created(location).body(createdArtistSocialMediaDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing artistSocialMediaService", description = "Updates and returns the artistSocialMediaService with the provided ID using the given data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ArtistSocialMediaService updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistSocialMediaDTO.class))),
            @ApiResponse(responseCode = "404", description = "ArtistSocialMediaService not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistSocialMediaDTO> update(
            @Parameter(description = "ID of the artistSocialMediaService to be updated", example = "1")
            @PathVariable Long id,
            @Schema(description = "Updated artistSocialMediaService data", requiredMode = Schema.RequiredMode.REQUIRED)
            @RequestBody ArtistSocialMediaDTO artistSocialMediaServiceDTO) {
        log.info("Updating artistSocialMediaService with ID: {}", id);
        ArtistSocialMedia artistSocialMedia = artistSocialMediaMapper.toEntity(artistSocialMediaServiceDTO);
        ArtistSocialMedia updatedArtistSocialMediaService = artistSocialMedia.update(id, artistSocialMedia);
        ArtistSocialMediaDTO artistSocialMediaServiceResponseDTO = artistSocialMediaMapper.toDTO(updatedArtistSocialMediaService);
        return ResponseEntity.ok(artistSocialMediaServiceResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an artistSocialMediaService by ID", description = "Deletes the artistSocialMediaService with the specified ID if found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "ArtistSocialMediaService deleted successfully"),
            @ApiResponse(responseCode = "404", description = "ArtistSocialMediaService not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the artistSocialMediaService to be deleted", example = "1")
            @PathVariable Long id) {
        log.info("Deleting artistSocialMediaService by ID: {}", id);
        artistSocialMediaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
