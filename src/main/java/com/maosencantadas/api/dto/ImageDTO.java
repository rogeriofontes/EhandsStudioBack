package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "ImageDTO", description = "DTO representing an image")
public class ImageDTO {

    @Schema(description = "Image identifier", example = "1")
    private Long id;

    @Schema(description = "Image name", example = "image.jpg")
    private String name;

    @Schema(description = "Folder where the image is saved", example = "/uploads")
    private String folder;

    @Schema(description = "Artist ID associated with the image", example = "2")
    private Long artist;

    @Schema(description = "Product ID associated with the image", example = "5")
    private Long product;

    @Schema(description = "Category ID associated with the image", example = "3")
    private Long category;
}
