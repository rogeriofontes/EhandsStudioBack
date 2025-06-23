package com.maosencantadas.model.domain.product;

import com.maosencantadas.model.domain.AuditDomain;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.domain.media.Media;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_product")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
@Schema(name = "Product", description = "Represents a product")
public class Product extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Product identifier", example = "1")
    private Long id;

    @Size(max = 100, message = "Name must be at most 100 characters")
    @Column(name = "name")
    @Schema(description = "Name of the product", example = "Horse Sculpture")
    private String name;

    @Size(max = 255, message = "Description must be at most 255 characters")
    @Column(name = "description")
    @Schema(description = "Description of the product", example = "Handcrafted horse sculpture made from recycled materials.")
    private String description;

    @Size(max = 50, message = "Size must be at most 50 characters")
    @Column(name = "size")
    @Schema(description = "Size of the product", example = "Medium")
    private String size;

    @Column(name = "image_url")
    @Schema(description = "Image URL of the product", example = "https://example.com/product-image.jpg")
    private String imageUrl;

    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    @Column(name = "price")
    @Schema(description = "Price of the product", example = "150.00")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_id", nullable = false)
    @Schema(description = "Product's media")
    private Media media;
}
