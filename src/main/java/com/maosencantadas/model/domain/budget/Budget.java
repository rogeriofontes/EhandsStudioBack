package com.maosencantadas.model.domain.budget;

import com.maosencantadas.model.domain.AuditDomain;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_budget")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
@ToString
@Schema(name = "Budget", description = "Represents a budget or quotation for a product requested by a customer")
public class Budget extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier of the budget", example = "1")
    private Long id;

    @NotBlank(message = "Status is required")
    @Size(max = 50, message = "Status must be at most 50 characters")
    @Column(name = "budget_status")
    @Schema(description = "Current status of the budget", example = "Pending")
    private String budgetStatus;

    @NotNull(message = "Budget date is required")
    @Column(name = "date_budget")
    @Schema(description = "Date and time the budget was created", example = "2024-04-27T15:30:00")
    private LocalDateTime dateBudget;

    @Size(max = 200, message = "Description must be at most 200 characteres")
    @Column(name = "description")
    @Schema(description = "Description of how the customer wants the product quote" , example = "The product must be pink")
    private String description;

    @Size(max = 200, message = "Response must be at most 200 characteres")
    @Column(name = "response")
    @Schema(description = "Response that the artist gives to the client about the budget" , example = "The product is ready in five days and costs this amount")
    private String response;

    @Column(name = "image_url")
    @Schema(description = "URL of the image associated with the budget", example = "https://example.com/image.png")
    private String imageUrl;

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

    @ManyToOne
    @NotNull(message = "Artist ID is required")
    @JoinColumn(name = "artist_id", nullable = false)
    @Schema(description = "ID of the artist who will handle the budget", example = "2")
    @ToString.Exclude
    private Artist artist;

}
