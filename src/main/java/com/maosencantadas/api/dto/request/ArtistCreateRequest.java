package com.maosencantadas.api.dto.request;

import com.maosencantadas.api.dto.ArtistDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "ArtistCreateRequest", description = "DTO representing a category of products")
public class ArtistCreateRequest implements Serializable {
    private UserRequest user;
    private String personType; // "NATURAL" ou "LEGAL"
    private PersonNaturalRequest personNatural;
    private PersonLegalRequest personLegal;
    private ArtistDTO artist;
}
