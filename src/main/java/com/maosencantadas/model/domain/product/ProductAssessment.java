package com.maosencantadas.model.domain.product;

import com.maosencantadas.model.domain.AuditDomain;
import com.maosencantadas.model.domain.customer.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_product_assessment")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAssessment extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    private Integer rating;

    @ManyToOne
    @NotNull(message = "Customer ID is required")
    @JoinColumn(name = "customer_id", nullable = false)
    @Schema(description = "ID of the customer who requested the budget", example = "5")
    @ToString.Exclude
    private Customer customer;

    @ManyToOne
    @NotNull(message = "Product ID is required")
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "ID of the product related to the budget", example = "3")
    @ToString.Exclude
    private Product product;

    @Size(max = 200, message = "Description must be at most 200 characteres")
    @Column(name = "message")
    @Schema(description = "Description of how the customer wants the product quote", example = "The product must be pink")
    private String message;

    @Schema(description = "Date and time when the message was sent", example = "2023-10-01T12:00:00")
    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

}