package com.maosencantadas.api.controller;

import com.maosencantadas.api.assembly.ArtistAssembly;
import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.api.dto.request.ArtistCreateRequest;
import com.maosencantadas.api.dto.response.ArtistResponse;
import com.maosencantadas.api.mapper.ArtistMapper;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.service.ArtistService;
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
@RequestMapping("/v1/artists")
@CrossOrigin(origins = "*")
@Tag(name = "Artist", description = "Operations related to Artists")
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistMapper artistMapper;
    private final ArtistAssembly artistAssembly;

    @GetMapping
    @Operation(summary = "List all artists", description = "Returns a list of all registered artists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artists successfully listed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ArtistDTO>> findAll() {
        log.info("Listing all artists");
        List<Artist> artists = artistService.findAll();
        List<ArtistDTO> artistDTOS = artistMapper.toDTO(artists);
        return ResponseEntity.ok(artistDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find artist by ID", description = "Retrieves a single artist by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistDTO.class))),
            @ApiResponse(responseCode = "404", description = "Artist not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistDTO> findById(
            @Parameter(description = "Unique ID of the artist", example = "1")
            @PathVariable Long id) {
        log.info("Finding artist by ID: {}", id);
        Artist artist = artistService.findById(id);
        ArtistDTO artistDTO = artistMapper.toDTO(artist);
        return ResponseEntity.of(Optional.ofNullable(artistDTO));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Find artist by ID", description = "Retrieves a single artist by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistDTO.class))),
            @ApiResponse(responseCode = "404", description = "Artist not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistDTO> findByUserId(
            @Parameter(description = "Unique ID of the artist", example = "1")
            @PathVariable("userId") Long userId) {
        log.info("Finding artist by User ID: {}", userId);
        Artist artist = artistService.findByUserId(userId);
        ArtistDTO artistDTO = artistMapper.toDTO(artist);
        return ResponseEntity.of(Optional.ofNullable(artistDTO));
    }

    @PostMapping
    @Operation(summary = "Create a new artist", description = "Creates and returns a new artist with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artist created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistResponse> create(
            @Schema(description = "New artist data", requiredMode = Schema.RequiredMode.REQUIRED)
            @RequestBody ArtistCreateRequest artistCreateRequest) {

        ArtistResponse artistResponse = artistAssembly.create(artistCreateRequest);
        log.info("Artist created successfully: {}", artistResponse.getPersonResponse().getName());
        URI location = RestUtils.getUri(artistResponse.getId());

        return ResponseEntity.created(location).body(artistResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing artist", description = "Updates and returns the artist with the provided ID using the given data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistDTO.class))),
            @ApiResponse(responseCode = "404", description = "Artist not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistDTO> update(
            @Parameter(description = "ID of the artist to be updated", example = "1")
            @PathVariable Long id,
            @Schema(description = "Updated artist data", requiredMode = Schema.RequiredMode.REQUIRED)
            @RequestBody ArtistDTO artistDTO) {
        log.info("Updating artist with ID: {}", id);
        Artist artist = artistMapper.toEntity(artistDTO);
        Artist updatedArtist = artistService.update(id, artist);
        ArtistDTO artistResponseDTO = artistMapper.toDTO(updatedArtist);
        return ResponseEntity.ok(artistResponseDTO);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "List artists by category ID", description = "Returns a list of all artists associated with the specified category ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artists successfully listed by category ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category or artists not found for the provided ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ArtistDTO>> getByArtistCategoryId(
            @Parameter(description = "ID of the category", example = "1")
            @PathVariable Long categoryId) {
        log.info("Listing artists for category ID: {}", categoryId);
        List<Artist> artists = artistService.findByArtistCategoryId(categoryId);
        if (artists.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ArtistDTO> artistDTOS = artistMapper.toDTO(artists);
        return ResponseEntity.ok(artistDTOS);
    }

    @GetMapping("/category/name/{categoryName}")
    @Operation(summary = "List artists by category name", description = "Returns a list of all artists associated with the specified category name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artists successfully listed by category name",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArtistDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ArtistDTO>> findByCategoryName(
            @Parameter(description = "Name of the category", example = "Rock")
            @PathVariable String categoryName) {
        log.info("Listing artists for category name: {}", categoryName);
        List<Artist> artists = artistService.findByCategoryName(categoryName);
        if (artists.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ArtistDTO> artistDTOS = artistMapper.toDTO(artists);
        return ResponseEntity.ok(artistDTOS);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an artist by ID", description = "Deletes the artist with the specified ID if found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Artist deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Artist not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the artist to be deleted", example = "1")
            @PathVariable Long id) {
        log.info("Deleting artist by ID: {}", id);
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
