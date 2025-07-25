package com.maosencantadas.model.domain.budget;

import com.maosencantadas.model.domain.AuditDomain;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_budget_media")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
@ToString
@Schema(name = "BudgetMedia", description = "Represents a budget or quotation for a product requested by a customer")
public class BudgetMedia extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier of the budget", example = "1")
    private Long id;

    @ManyToOne
    @NotNull(message = "Budget ID is required")
    @JoinColumn(name = "budget_id", nullable = false)
    @Schema(description = "ID of the budget who will handle the budget", example = "2")
    @ToString.Exclude
    private Budget budget;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_id", nullable = false)
    @Schema(description = "Budget's media")
    private Media media;
}
