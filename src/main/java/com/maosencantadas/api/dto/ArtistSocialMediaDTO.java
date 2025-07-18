package com.maosencantadas.api.dto;

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
public class ArtistSocialMediaDTO {

    @Schema(description = "Identify the artist", example = "1")
    private Long id;

    @Schema(description = "Artist's Instagram profile", example = "@testingartista")
    private String instagram;

    @Schema(description = "Artist's Facebook profile", example = "facebook.com/testingartista")
    private String facebook;

    @Schema(description = "Artist's WhatsApp number", example = "(11) 91234-5678")
    private String twitterX;

    @Schema(description = "Artist's WhatsApp number", example = "(11) 91234-5678")
    private String linkedin;

    @Schema(description = "Artist's TikTok profile", example = "@testingartista")
    private String tiktok;

    @Schema(description = "Artist's YouTube channel", example = "youtube.com/testingartista")
    private String youtube;

    @Schema(description = "Artist's Pinterest profile", example = "pinterest.com/testingartista")
    private String pinterest;

    @Schema(description = "Artist's Telegram profile", example = "@testingartista")
    private String telegram;

    @Schema(description = "Artist's website", example = "www.testingartista.com")
    private String website;

    @Schema(description = "User ID associated with the Artist", example = "id: 2, login: test@test.com, password: ******, UserRole: ARTIST")
    private Long artistId;
}
