package com.maosencantadas.model.domain.image;

import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.domain.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Image", description = "Represents images linked to artists, products, or categories")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Image identifier", example = "1")
    private Long id;

    @Column(name = "name")
    @Schema(description = "Image name", example = "image.jpg")
    private String name;

    @Column(name = "folder")
    @Schema(description = "Folder where the image is saved", example = "/uploads")
    private String folder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @Schema(description = "Category associated with the image")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    @Schema(description = "Artist associated with the image")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "Product associated with the image")
    private Product product;
}
