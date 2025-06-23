package com.maosencantadas.api.dto;

import com.maosencantadas.model.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Valid

@Schema(name = "ArtistDTO", description = "DTO representing an artist")
public class ArtistDTO {

    @Schema(description = "Identify the artist", example = "1")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Schema(description = "Artist name", example = "Amanda")
    private String name;

    @Schema(description = "Artist photo URL", example = "https://testing.com/foto.jpg")
    private String imageUrl;

    @Size(max = 200, message = "Address must have a maximum of 200 characters")
    @Schema(description = "Artist address", example = "Rua Padre Pio, 123 - Centro")
    private String address;

    @Email(message = "Invalid email")
    @Schema(description = "Artist email", example = "testing@example.com")
    private String email;

    @Size(max = 20, message = "Phone number must have a maximum of 20 characters")
    @Schema(description = "Artist's phone number", example = "(34) 98765-4321")
    private String phone;

    @Schema(description = "Artist's Instagram profile", example = "@testingartista")
    private String insta;

    @Schema(description = "Artist's Facebook profile", example = "facebook.com/testingartista")
    private String face;

    @Schema(description = "Artist's WhatsApp number", example = "(11) 91234-5678")
    private String whatsapp;

    @NotBlank(message = "CPF is mandatory")
    @Schema(description = "Artist's CPF", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Category ID", example = "1")
    private Long categoryId;

    @Schema(description = "User ID associated with the Artist", example = "id: 2, login: test@test.com, password: ******, UserRole: ARTIST")
    private Long userId;

    @Schema(description = "Media ID", example = "1")
    private Long mediaId;
}
