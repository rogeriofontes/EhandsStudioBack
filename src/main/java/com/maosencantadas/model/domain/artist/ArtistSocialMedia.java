package com.maosencantadas.model.domain.artist;

import com.maosencantadas.model.domain.AuditDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_artist_social_media")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
@ToString
@Schema(name = "Artist Social Media", description = "represents an artist")
public class ArtistSocialMedia extends AuditDomain {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identify the artist", example = "1")
    private Long id;

    @Schema(description = "Artist's Instagram profile", example = "@testingartista")
    private String instagram;

    @Schema(description = "Artist's Facebook profile", example = "facebook.com/testingartista")
    private String facebook;

    @Schema(description = "Artist's WhatsApp number", example = "(11) 91234-5678")
    @Column(name = "twitter_x")
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

    @OneToOne
    @JoinColumn(name = "artist_id", unique = true, nullable = false)
    private Artist artist;

    public ArtistSocialMedia update(Long id, ArtistSocialMedia artistSocialMedia) {
        this.id = id;
        this.setInstagram(artistSocialMedia.getInstagram());
        this.setFacebook(artistSocialMedia.getFacebook());
        this.setTwitterX(artistSocialMedia.getTwitterX());
        this.setLinkedin(artistSocialMedia.getLinkedin());
        this.setTiktok(artistSocialMedia.getTiktok());
        this.setYoutube(artistSocialMedia.getYoutube());
        this.setPinterest(artistSocialMedia.getPinterest());
        this.setTelegram(artistSocialMedia.getTelegram());
        this.setWebsite(artistSocialMedia.getWebsite());
        if (artistSocialMedia.getArtist() != null) {
            this.artist = artistSocialMedia.getArtist();
        }
        return this;
    }
}

