package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ArtistCategoryDTO;
import com.maosencantadas.api.mapper.ArtistCategoryMapper;
import com.maosencantadas.model.domain.artist.ArtistCategory;
import com.maosencantadas.model.service.ArtistCategoryService;
import com.maosencantadas.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
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

@Slf4j
@RestController
@RequestMapping("/v1/artist-categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Category", description = "Operations related to artist categories")
public class ArtistCategoryController {

    private final ArtistCategoryService categoryService;
    private final ArtistCategoryMapper categoryMapper;

    @GetMapping
    @Operation(summary = "List all categories", description = "Returns a list of all available categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories successfully listed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistCategoryDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ArtistCategoryDTO>> findAll() {
        log.info("Listing all categories");
        List<ArtistCategory> categories = categoryService.findAll();
        List<ArtistCategoryDTO> artistCategoryDTOS = categoryMapper.toDTO(categories);
        return ResponseEntity.ok(artistCategoryDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find category by ID", description = "Returns a specific category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistCategoryDTO> findById(@PathVariable("id") Long id) {
        log.info("Finding category by ID: {}", id);
        ArtistCategory artistCategory = categoryService.findById(id);
        ArtistCategoryDTO artistCategoryDTO = categoryMapper.toDTO(artistCategory);
        log.info("Category found: {}", artistCategoryDTO.getName());
        return ResponseEntity.ok(artistCategoryDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates and returns a new category with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistCategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistCategoryDTO> create(
            @RequestBody
            @Schema(description = "New category data", requiredMode = Schema.RequiredMode.REQUIRED)
            ArtistCategoryDTO artistCategoryDTO) {
        log.info("Creating new category: {}", artistCategoryDTO.getName());
        ArtistCategory artistCategory = categoryMapper.toEntity(artistCategoryDTO);
        ArtistCategory newArtistCategory = categoryService.save(artistCategory);

        log.info("Category created with ID: {}", newArtistCategory.getId());
        ArtistCategoryDTO newArtistCategoryDTO = categoryMapper.toDTO(newArtistCategory);
        URI location = RestUtils.getUri(newArtistCategoryDTO.getId());
        return ResponseEntity.created(location).body(newArtistCategoryDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category", description = "Update an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ArtistCategoryDTO> update(
            @PathVariable("id")
            @Schema(description = "ID of the category to be updated", example = "1")
            Long id,
            @RequestBody
            @Schema(description = "Updated category data", requiredMode = Schema.RequiredMode.REQUIRED)
            ArtistCategoryDTO artistCategoryDTO) {
        log.info("Updating category with id: {}", id);
        ArtistCategory artistCategory = categoryMapper.toEntity(artistCategoryDTO);
        ArtistCategory updatedArtistCategory = categoryService.update(id, artistCategory);

        log.info("Category updated: {}", updatedArtistCategory.getName());
        ArtistCategoryDTO updatedArtistCategoryDTO = categoryMapper.toDTO(updatedArtistCategory);
        return ResponseEntity.ok(updatedArtistCategoryDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by ID", description = "Deletes an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(
            @PathVariable("id")
            @Schema(description = "ID of the category to be deleted", example = "1")
            Long id) {
        log.info("Deleting category by ID: {}", id);
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
