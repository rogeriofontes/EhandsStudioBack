package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "ProductDTO", description = "DTO representing a product")
public class ProductDTO {

    @Schema(description = "Product identifier", example = "1")
    private Long id;

    @Size(max = 100, message = "Name must be at most 100 characters")
    @Schema(description = "Name of the product", example = "Horse Sculpture")
    private String name;

    @Size(max = 255, message = "Description must be at most 255 characters")
    @Schema(description = "Description of the product", example = "Handcrafted horse sculpture made from recycled materials.")
    private String description;

    @Size(max = 255, message = "Technical data must be at most 255 characters")
    @Schema(description = "Technical data of the product", example = "Material: Recycled Metal; Dimensions: 30x20x15 cm")
    private String technicalData;

    @Size(max = 50, message = "Size must be at most 50 characters")
    @Schema(description = "Size of the product", example = "Medium")
    private String size;

    @Schema(description = "Image URL of the product", example = "https://example.com/product-image.jpg")
    private String imageUrl;

    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    @Schema(description = "Price of the product", example = "150.00")
    private BigDecimal price;

    @Schema(description = "Tags associated with the product", example = "recycled, sculpture, horse")
    private int discount;

    @Schema(description = "ID of the product's category or Name of the product's category", example = "2, Arts em Geral")
    private Long productCategoryId;

    @Schema(description = "ID of the product's artist or Name of the product's artist", example = "5, Gerald")
    private Long artistId;

    @Schema(description = "Media ID", example = "1")
    private Long mediaId;

    private Set<Long> tagIds; // Lista de IDs das tags selecionadas pelo usuário
}
