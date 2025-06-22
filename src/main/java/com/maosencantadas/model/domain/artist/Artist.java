package com.maosencantadas.model.domain.artist;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.maosencantadas.model.domain.AuditDomain;
import com.maosencantadas.model.domain.category.Category;
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

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identify the artist", example = "1")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Schema(description = "Artist name", example = "Amanda")
    private String name;

    @Schema(description = "Artist photo URL", example = "https://testing.com/foto.jpg")
    @Column(name="image_url")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @Schema(description = "Artist's category")
    private Category category;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    @Schema(description = "Artist user")
    private User user;
}

