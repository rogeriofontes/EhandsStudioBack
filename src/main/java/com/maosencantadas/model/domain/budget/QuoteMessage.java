package com.maosencantadas.model.domain.budget;

import com.maosencantadas.model.domain.AuditDomain;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_budget_quote_message")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
@ToString
@Schema(name = "Budget", description = "Represents a budget or quotation for a product requested by a customer")
public class QuoteMessage extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier of the budget", example = "1")
    private Long id;

    @ManyToOne
    @NotNull(message = "Product ID is required")
    @JoinColumn(name = "budget_id", nullable = false)
    @Schema(description = "ID of the product related to the budget", example = "3")
    @ToString.Exclude
    private Budget budget;

    @Size(max = 200, message = "Description must be at most 200 characteres")
    @Column(name = "message")
    @Schema(description = "Description of how the customer wants the product quote", example = "The product must be pink")
    private String message;

    @Schema(description = "Date and time when the message was sent", example = "2023-10-01T12:00:00")
    @Column(name = "sent_by_customer", nullable = false)
    private Boolean sentByCustomer;

    @Schema(description = "Date and time when the message was sent", example = "2023-10-01T12:00:00")
    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

}
