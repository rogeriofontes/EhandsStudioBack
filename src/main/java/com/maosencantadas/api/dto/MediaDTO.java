package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "ImageDTO", description = "DTO representing an image")
public class MediaDTO {

    @Schema(description = "Image identifier", example = "1")
    private Long id;

    @Schema(description = "Image name", example = "image.jpg")
    private String name;

    @Schema(description = "Folder where the image is saved", example = "/uploads")
    private String folder;

    @Schema(description = "Media type (IMAGE, VIDEO, AUDIO, DOCUMENT)", example = "IMAGE")
    private String type;
}
