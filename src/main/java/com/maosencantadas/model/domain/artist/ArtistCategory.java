package com.maosencantadas.model.domain.artist;

import com.maosencantadas.model.domain.AuditDomain;
import com.maosencantadas.model.domain.media.Media;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_artist_category")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
@ToString
@Schema(name = "Artist Category", description = "represents an artist")
public class ArtistCategory extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifies the category", example = "1")
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Column(name = "name")
    @Schema(description = "Category name", example = "TestingCategory")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_id", nullable = false)
    @Schema(description = "Customer's media")
    private Media media;
}
