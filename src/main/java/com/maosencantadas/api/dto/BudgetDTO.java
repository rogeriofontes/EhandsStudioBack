package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(name = "BudgetDTO", description = "DTO representing a budget")
public class BudgetDTO {

    @Schema(description = "Unique identifier of the budget", example = "1")
    private Long id;

    @NotBlank(message = "Status is required")
    @Size(max = 50, message = "Status must be at most 50 characters")
    @Schema(description = "Current status of the budget", example = "Pending")
    private String status;

    @NotNull(message = "Budget date is required")
    @Schema(description = "Date and time the budget was created", example = "2024-04-27T15:30:00")
    private LocalDateTime dateBudget;

    @Schema(description = "Description of how the customer wants the product quote" , example = "The product must be pink")
    private String description;

    @Schema(description = "Response that the artist gives to the client about the budget" , example = "The product is ready in five days and costs this amount")
    private String response;

    @Schema(description = "URL of the image associated with the budget", example = "https://example.com/image.png")
    private String imageUrl;

    @NotNull(message = "Customer ID is required")
    @Schema(description = "ID of the customer who requested the budget", example = "5")
    private Long customer;

    @NotNull(message = "Product ID is required")
    @Schema(description = "ID of the product related to the budget", example = "3")
    private Long product;
}
