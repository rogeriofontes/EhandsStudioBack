package com.maosencantadas.api.dto;

import com.maosencantadas.model.domain.budget.BudgetStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "BudgetDTO", description = "DTO representing a budget")
public class BudgetDTO {

    @Schema(description = "Unique identifier of the budget", example = "1")
    private Long id;

    @NotBlank(message = "Status is required")
    @Schema(description = "Current status of the budget", example = "Pending")
    private BudgetStatus budgetStatus;

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
    private Long customerId;

    @NotNull(message = "Product ID is required")
    @Schema(description = "ID of the product related to the budget", example = "3")
    private Long productId;

    @NotNull(message = "Product ID is required")
    @Schema(description = "ID of the product related to the budget", example = "3")
    private Long artistId;

    @Schema(description = "Media ID", example = "1")
    private Long mediaId;
}
