package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "CategoryDTO", description = "DTO representing a category of products")
public class ProductCategoryDTO {

    @Schema(description = "Identifies the category", example = "1")
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Schema(description = "Category name", example = "TestingCategory")
    private String name;

    @Schema(description = "Media ID", example = "1")
    private Long mediaId;
}
