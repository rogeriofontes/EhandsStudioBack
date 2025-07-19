package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ProductAssessmentDTO;
import com.maosencantadas.api.mapper.ProductAssessmentMapper;
import com.maosencantadas.model.domain.product.ProductAssessment;
import com.maosencantadas.model.service.ProductAssessmentService;
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
@RequestMapping("/v1/product-assessments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Assessment", description = "Operations related to product categories")
public class ProductAssessmentController {

    private final ProductAssessmentService productAssessmentService;
    private final ProductAssessmentMapper productAssessmentMapper;

    @GetMapping
    @Operation(summary = "List all categories", description = "Returns a list of all available categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories successfully listed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductAssessmentDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ProductAssessmentDTO>> findAll() {
        log.info("Listing all categories");
        List<ProductAssessment> productAssessments = productAssessmentService.findAll();
        List<ProductAssessmentDTO> productAssessmentDTOS = productAssessmentMapper.toDtoList(productAssessments);
        return ResponseEntity.ok(productAssessmentDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find category by ID", description = "Returns a specific category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assessment found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductAssessmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assessment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductAssessmentDTO> findById(@PathVariable("id") Long id) {
        log.info("Finding category by ID: {}", id);
        ProductAssessment productAssessment = productAssessmentService.findById(id);
        ProductAssessmentDTO productAssessmentDTO = productAssessmentMapper.toDto(productAssessment);
        log.info("Assessment found: {}", productAssessmentDTO.getName());
        return ResponseEntity.ok(productAssessmentDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates and returns a new category with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Assessment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductAssessmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductAssessmentDTO> create(
            @RequestBody
            @Schema(description = "New category data", requiredMode = Schema.RequiredMode.REQUIRED)
            ProductAssessmentDTO productAssessmentDTO) {
        log.info("Creating new category: {}", productAssessmentDTO.getName());
        ProductAssessment productAssessment = productAssessmentMapper.toEntity(productAssessmentDTO);
        ProductAssessment newProductAssessment = productAssessmentService.save(productAssessment);

        log.info("Assessment created with ID: {}", newProductAssessment.getId());
        ProductAssessmentDTO newProductAssessmentDTO = productAssessmentMapper.toDto(newProductAssessment);
        URI location = RestUtils.getUri(newProductAssessmentDTO.getId());
        return ResponseEntity.created(location).body(newProductAssessmentDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category", description = "Update an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assessment updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductAssessmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assessment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductAssessmentDTO> update(
            @PathVariable("id")
            @Schema(description = "ID of the category to be updated", example = "1")
            Long id,
            @RequestBody
            @Schema(description = "Updated category data", requiredMode = Schema.RequiredMode.REQUIRED)
            ProductAssessmentDTO productAssessmentDTO) {
        log.info("Updating category with id: {}", id);
        ProductAssessment productAssessment = productAssessmentMapper.toEntity(productAssessmentDTO);
        ProductAssessment updatedProductAssessment = productAssessmentService.update(id, productAssessment);

        log.info("Assessment updated: {}", updatedProductAssessment.getName());
        ProductAssessmentDTO updatedProductAssessmentDTO = productAssessmentMapper.toDto(updatedProductAssessment);
        return ResponseEntity.ok(updatedProductAssessmentDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by ID", description = "Deletes an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assessment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Assessment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(
            @PathVariable("id")
            @Schema(description = "ID of the category to be deleted", example = "1")
            Long id) {
        log.info("Deleting category by ID: {}", id);
        productAssessmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
