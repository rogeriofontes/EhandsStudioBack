package com.maosencantadas.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Valid
@Schema(name = "ArtistDTO", description = "DTO representing an artist")
public class CustomerResponse {
    private Long id;
    private String token;
    private PersonResponse personResponse;
    private CustomerLegalResponse customerLegalResponse;
    private CustomerNaturalResponse customerNaturalResponse;
}
