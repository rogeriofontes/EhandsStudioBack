package com.maosencantadas.model.domain.artist;

import com.maosencantadas.model.domain.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "artists")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Schema(name = "Artist", description = "represents an artist")

public class Artist {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identify the artist", example = "1")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Column(name="name")
    @Schema(description = "Artist name", example = "Amanda")
    private String name;

    @Column(name="image_url")
    @Schema(description = "Artist photo URL", example = "https://testing.com/foto.jpg")
    private String imageUrl;

    @Size(max = 200, message = "Address must have a maximum of 200 characters")
    @Column(name="address")
    @Schema(description = "Artist address", example = "Rua Padre Pio, 123 - Centro")
    private String address;

    @Email(message = "Invalid email")
    @Column(name="email")
    @Schema(description = "Artist email", example = "testing@example.com")
    private String email;

    @Size(max = 20, message = "Phone number must have a maximum of 20 characters")
    @Column(name="phone")
    @Schema(description = "Artist's phone number", example = "(34) 98765-4321")
    private String phone;

    @Schema(description = "Artist's Instagram profile", example = "@testingartista")
    @Column(name="insta")
    private String insta;

    @Schema(description = "Artist's Facebook profile", example = "facebook.com/testingartista")
    @Column(name="face")
    private String face;

    @Schema(description = "Artist's WhatsApp number", example = "(11) 91234-5678")
    @Column(name="whatsapp")
    private String whatsapp;

    @NotBlank(message = "CPF is mandatory")
    @Column(name="cpf")
    @Schema(description = "Artist's CPF", example = "123.456.789-00")
    private String cpf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @Schema(description = "Artist's category")
    private Category category;
}

