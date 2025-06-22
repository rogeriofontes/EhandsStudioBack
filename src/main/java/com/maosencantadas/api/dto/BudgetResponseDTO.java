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
@Schema(name = "BudgetResponseDTO", description = "DTO representing a budget")
public class BudgetResponseDTO {

    @Schema(description = "Response that the artist gives to the client about the budget" , example = "The product is ready in five days and costs this amount")
    private String response;
}
