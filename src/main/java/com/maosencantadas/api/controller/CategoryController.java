package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.CategoryDTO;
import com.maosencantadas.api.mapper.CategoryMapper;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.service.CategoryService;
import com.maosencantadas.utils.RestUtils;
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
    private final CategoryMapper categoryMapper;

    @GetMapping
    @Operation(summary = "List all categories", description = "Returns a list of all available categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories successfully listed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CategoryDTO>> findAll() {
        log.info("Listing all categories");
        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoryDTOS = categoryMapper.toDTO(categories);
        return ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find category by ID", description = "Returns a specific category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long id) {
        log.info("Finding category by ID: {}", id);
        Category category = categoryService.findById(id);
        CategoryDTO categoryDTO = categoryMapper.toDTO(category);
        log.info("Category found: {}", categoryDTO.getName());
        return ResponseEntity.ok(categoryDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates and returns a new category with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CategoryDTO> create(
            @RequestBody
            @Schema(description = "New category data", requiredMode = Schema.RequiredMode.REQUIRED)
            CategoryDTO categoryDTO) {
        log.info("Creating new category: {}", categoryDTO.getName());
        Category category = categoryMapper.toEntity(categoryDTO);
        Category newCategory = categoryService.save(category);

        log.info("Category created with ID: {}", newCategory.getId());
        CategoryDTO newCategoryDTO = categoryMapper.toDTO(newCategory);
        URI location = RestUtils.getUri(newCategoryDTO.getId());
        return ResponseEntity.created(location).body(newCategoryDTO);
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
    public ResponseEntity<CategoryDTO> update(
            @PathVariable("id")
            @Schema(description = "ID of the category to be updated", example = "1")
            Long id,
            @RequestBody
            @Schema(description = "Updated category data", requiredMode = Schema.RequiredMode.REQUIRED)
            CategoryDTO categoryDTO) {
        log.info("Updating category with id: {}", id);
        Category category = categoryMapper.toEntity(categoryDTO);
        Category updatedCategory = categoryService.update(id, category);

        log.info("Category updated: {}", updatedCategory.getName());
        CategoryDTO updatedCategoryDTO = categoryMapper.toDTO(updatedCategory);
        return ResponseEntity.ok(updatedCategoryDTO);
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
