package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.CategoryDTO;
import com.maosencantadas.model.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Category", description = "Operations related to product categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "List all categories", description = "Returns a list of all available categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories successfully listed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {
        log.info("Listing all categories");
        List<CategoryDTO> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find category by ID", description = "Returns a specific category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable("id") Long id) {
        log.info("Finding category by ID: {}", id);
        CategoryDTO categoryDTO = categoryService.findCategoryById(id);
        return categoryDTO != null
                ? ResponseEntity.ok(categoryDTO)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates and returns a new category with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CategoryDTO> createCategory(
            @RequestBody
            @Schema(description = "New category data", required = true)
            CategoryDTO categoryDTO) {
        log.info("Creating new category: {}", categoryDTO.getName());
        CategoryDTO newCategory = categoryService.saveCategory(categoryDTO);
        URI location = URI.create(String.format("/v1/categories/%s", newCategory.getId()));
        return ResponseEntity.created(location).body(newCategory);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category", description = "Update an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable("id")
            @Schema(description = "ID of the category to be updated", example = "1")
            Long id,
            @RequestBody
            @Schema(description = "Updated category data", required = true)
            CategoryDTO categoryDTO) {
        log.info("Updating category with id: {}", id);
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by ID", description = "Deletes an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteCategory(
            @PathVariable("id")
            @Schema(description = "ID of the category to be deleted", example = "1")
            Long id) {
        log.info("Deleting category by ID: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
