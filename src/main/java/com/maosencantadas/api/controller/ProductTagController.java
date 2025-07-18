package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ProductTagDTO;
import com.maosencantadas.api.mapper.ProductTagMapper;
import com.maosencantadas.model.domain.product.ProductTag;
import com.maosencantadas.model.domain.product.ProductTag;
import com.maosencantadas.model.service.ProductTagService;
import com.maosencantadas.model.service.ProductTagService;
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
@RequestMapping("/v1/product-tags")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Tag", description = "Operations related to product categories")
public class ProductTagController {

    private final ProductTagService productTagService;
    private final ProductTagMapper productTagMapper;

    @GetMapping
    @Operation(summary = "List all categories", description = "Returns a list of all available categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories successfully listed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductTagDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ProductTagDTO>> findAll() {
        log.info("Listing all categories");
        List<ProductTag> categories = productTagService.findAll();
        List<ProductTagDTO> productTagDTOS = productTagMapper.toDTO(categories);
        return ResponseEntity.ok(productTagDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find tag by ID", description = "Returns a specific tag by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductTagDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductTagDTO> findById(@PathVariable("id") Long id) {
        log.info("Finding tag by ID: {}", id);
        ProductTag productTag = productTagService.findById(id);
        ProductTagDTO productTagDTO = productTagMapper.toDTO(productTag);
        log.info("Tag found: {}", productTagDTO.getName());
        return ResponseEntity.ok(productTagDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new tag", description = "Creates and returns a new tag with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductTagDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductTagDTO> create(
            @RequestBody
            @Schema(description = "New tag data", requiredMode = Schema.RequiredMode.REQUIRED)
            ProductTagDTO productTagDTO) {
        log.info("Creating new tag: {}", productTagDTO.getName());
        ProductTag productTag = productTagMapper.toEntity(productTagDTO);
        ProductTag newProductTag = productTagService.save(productTag);

        log.info("Tag created with ID: {}", newProductTag.getId());
        ProductTagDTO newProductTagDTO = productTagMapper.toDTO(newProductTag);
        URI location = RestUtils.getUri(newProductTagDTO.getId());
        return ResponseEntity.created(location).body(newProductTagDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing tag", description = "Update an existing tag by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductTagDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductTagDTO> update(
            @PathVariable("id")
            @Schema(description = "ID of the tag to be updated", example = "1")
            Long id,
            @RequestBody
            @Schema(description = "Updated tag data", requiredMode = Schema.RequiredMode.REQUIRED)
            ProductTagDTO productTagDTO) {
        log.info("Updating tag with id: {}", id);
        ProductTag productTag = productTagMapper.toEntity(productTagDTO);
        ProductTag updatedProductTag = productTagService.update(id, productTag);

        log.info("Tag updated: {}", updatedProductTag.getName());
        ProductTagDTO updatedProductTagDTO = productTagMapper.toDTO(updatedProductTag);
        return ResponseEntity.ok(updatedProductTagDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tag by ID", description = "Deletes an existing tag by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Tag not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(
            @PathVariable("id")
            @Schema(description = "ID of the tag to be deleted", example = "1")
            Long id) {
        log.info("Deleting tag by ID: {}", id);
        productTagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
