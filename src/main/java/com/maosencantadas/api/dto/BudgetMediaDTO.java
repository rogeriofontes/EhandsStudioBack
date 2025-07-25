package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetMediaDTO {

    @Schema(description = "Unique identifier of the budget", example = "1")
    private Long id;

    @Schema(description = "ID of the budget who will handle the budget", example = "2")
    private Long budgetId;

    @Schema(description = "Budget's media")
    private Long mediaId;
}
