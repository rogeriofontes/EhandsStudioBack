package com.maosencantadas.model.domain.artist;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.maosencantadas.model.domain.AuditDomain;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.person.Person;
import com.maosencantadas.model.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_artist")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
@ToString
@Schema(name = "Artist", description = "represents an artist")
public class Artist extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @JsonManagedReference
    private User user;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Schema(description = "Artist photo URL", example = "https://testing.com/foto.jpg")
    @Column(name="image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_category_id", nullable = false)
    @Schema(description = "Artist's category")
    private ArtistCategory artistCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_id", nullable = false)
    @Schema(description = "Artist's media")
    private Media media;
}

