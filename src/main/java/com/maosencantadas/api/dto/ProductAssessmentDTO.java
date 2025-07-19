package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "CategoryDTO", description = "DTO representing a category of products")
public class ProductAssessmentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    private Integer rating;

    @ManyToOne
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @ManyToOne
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Size(max = 200, message = "Description must be at most 200 characteres")
    @Schema(description = "Description of how the customer wants the product quote", example = "The product must be pink")
    private String message;

    @Schema(description = "Date and time when the message was sent", example = "2023-10-01T12:00:00")
    private LocalDateTime sentAt;
}
