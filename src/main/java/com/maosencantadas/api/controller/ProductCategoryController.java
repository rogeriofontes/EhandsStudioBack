package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ProductCategoryDTO;
import com.maosencantadas.api.mapper.ProductCategoryMapper;
import com.maosencantadas.model.domain.product.ProductCategory;
import com.maosencantadas.model.service.ProductCategoryService;
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
@RequestMapping("/v1/product-categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Category", description = "Operations related to product categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;
    private final ProductCategoryMapper productCategoryMapper;

    @GetMapping
    @Operation(summary = "List all categories", description = "Returns a list of all available categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories successfully listed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ProductCategoryDTO>> findAll() {
        log.info("Listing all categories");
        List<ProductCategory> categories = productCategoryService.findAll();
        List<ProductCategoryDTO> productCategoryDTOS = productCategoryMapper.toDTO(categories);
        return ResponseEntity.ok(productCategoryDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find category by ID", description = "Returns a specific category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductCategoryDTO> findById(@PathVariable("id") Long id) {
        log.info("Finding category by ID: {}", id);
        ProductCategory productCategory = productCategoryService.findById(id);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDTO(productCategory);
        log.info("Category found: {}", productCategoryDTO.getName());
        return ResponseEntity.ok(productCategoryDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates and returns a new category with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductCategoryDTO> create(
            @RequestBody
            @Schema(description = "New category data", requiredMode = Schema.RequiredMode.REQUIRED)
            ProductCategoryDTO productCategoryDTO) {
        log.info("Creating new category: {}", productCategoryDTO.getName());
        ProductCategory productCategory = productCategoryMapper.toEntity(productCategoryDTO);
        ProductCategory newProductCategory = productCategoryService.save(productCategory);

        log.info("Category created with ID: {}", newProductCategory.getId());
        ProductCategoryDTO newProductCategoryDTO = productCategoryMapper.toDTO(newProductCategory);
        URI location = RestUtils.getUri(newProductCategoryDTO.getId());
        return ResponseEntity.created(location).body(newProductCategoryDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category", description = "Update an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductCategoryDTO> update(
            @PathVariable("id")
            @Schema(description = "ID of the category to be updated", example = "1")
            Long id,
            @RequestBody
            @Schema(description = "Updated category data", requiredMode = Schema.RequiredMode.REQUIRED)
            ProductCategoryDTO productCategoryDTO) {
        log.info("Updating category with id: {}", id);
        ProductCategory productCategory = productCategoryMapper.toEntity(productCategoryDTO);
        ProductCategory updatedProductCategory = productCategoryService.update(id, productCategory);

        log.info("Category updated: {}", updatedProductCategory.getName());
        ProductCategoryDTO updatedProductCategoryDTO = productCategoryMapper.toDTO(updatedProductCategory);
        return ResponseEntity.ok(updatedProductCategoryDTO);
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
        productCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
