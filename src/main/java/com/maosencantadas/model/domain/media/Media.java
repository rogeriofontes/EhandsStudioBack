package com.maosencantadas.model.domain.media;

import com.maosencantadas.model.domain.AuditDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tb_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(name = "Media", description = "Represents media files like images, videos, audios, or documents")
public class Media extends AuditDomain {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    @Schema(description = "Media type (IMAGE, VIDEO, AUDIO, DOCUMENT)", example = "IMAGE")
    private MediaType type;
}
