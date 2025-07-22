package com.maosencantadas.api.assembly;

import com.maosencantadas.api.dto.UserDTO;
import com.maosencantadas.api.dto.request.PersonLegalRequest;
import com.maosencantadas.api.dto.request.PersonNaturalRequest;
import com.maosencantadas.api.dto.request.UserRequest;
import com.maosencantadas.api.dto.response.ArtistLegalResponse;
import com.maosencantadas.api.dto.response.ArtistNaturalResponse;
import com.maosencantadas.api.dto.response.ArtistResponse;
import com.maosencantadas.api.dto.response.PersonResponse;
import com.maosencantadas.api.mapper.PersonLegalMapper;
import com.maosencantadas.api.mapper.PersonNaturalMapper;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.person.PersonLegal;
import com.maosencantadas.model.domain.person.PersonNatural;
import com.maosencantadas.model.service.PersonLegalService;
import com.maosencantadas.model.service.PersonNaturalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtistAssembly {

    private final PersonNaturalMapper personNaturalMapper;
    private final PersonLegalMapper personLegalMapper;
    private final PersonNaturalService personNaturalService;
    private final PersonLegalService personLegalService;

    public PersonResponse buildPerson(Artist artist, UserDTO userDTO) {
        return PersonResponse.builder()
                .id(artist.getPerson().getId())
                .name(artist.getPerson().getName())
                .imageUrl(artist.getImageUrl())
                .address(artist.getPerson().getAddress())
                .email(artist.getPerson().getEmail())
                .phone(artist.getPerson().getPhone())
                .whatsapp(artist.getPerson().getWhatsapp())
                .artistCategoryId(artist.getArtistCategory().getId())
                .userId(userDTO.getId())
                .mediaId(artist.getMedia().getId() != null ? artist.getMedia().getId() : null)
                .build();
    }

    public boolean isUserRequestInvalid(UserRequest userRequest) {
        return userRequest.getEmail() == null || userRequest.getEmail().isEmpty()
                || userRequest.getRole() == null || userRequest.getRole().isEmpty();
    }

    public PersonNatural processNaturalPerson(PersonNaturalRequest personNaturalRequest) {
        if (personNaturalRequest == null || personNaturalRequest.getDocumentNumber() == null || personNaturalRequest.getDocumentNumber().isEmpty()) {
            log.error("CPF is required for natural person");
        }

        var personNatural = personNaturalMapper.toEntity(personNaturalRequest);
        var personNaturalSaved = personNaturalService.save(personNatural);
        if (personNaturalSaved == null) {
            log.error("Failed to create PersonNatural: {}", personNatural.getName());
        }

        return personNaturalSaved;
    }

    public ArtistNaturalResponse processNaturalPersonResponse(PersonNatural personNatural) {
        if (personNatural == null || personNatural.getDocumentNumber() == null || personNatural.getDocumentNumber().isEmpty()) {
            log.error("CPF is required for natural person");
        }

        var personNaturalDto = personNaturalMapper.toDTO(personNatural);
        log.info("PersonNatural created DTO: {}", personNaturalDto);

        if (personNaturalDto == null) {
            log.error("PersonNatural DTO is null for person: {}", personNatural != null ? personNatural.getName() : null);
            return null;
        }

        return ArtistNaturalResponse.builder()
                .cpf(personNatural != null ? personNatural.getDocumentNumber() : null)
                .documentNumber(personNatural != null ? personNatural.getDocumentNumber() : null)
                .identityNumber(personNatural != null ? personNatural.getIdentityNumber() : null)
                .birthDate(personNatural != null ? personNatural.getBirthDate() : null)
                .build();
    }

    public PersonLegal processLegalPerson(PersonLegalRequest personLegalRequest) {
        if (personLegalRequest == null || personLegalRequest.getDocumentNumber() == null || personLegalRequest.getDocumentNumber().isEmpty()) {
            log.error("CNPJ is required for legal person");
        }
        var personLegal = personLegalMapper.toEntity(personLegalRequest);
        var personLegalSaved = personLegalService.save(personLegal);
        if (personLegalSaved == null) {
            log.error("Failed to create PersonLegal: {}", personLegal.getName());
        }

        return personLegalSaved;
    }

    public ArtistLegalResponse processLegalPersonResponse(PersonLegal personLegal) {
        if (personLegal == null || personLegal.getDocumentNumber() == null || personLegal.getDocumentNumber().isEmpty()) {
            log.error("CNPJ is required for legal person");
        }

        var personLegalDto = personLegalMapper.toDTO(personLegal);
        log.info("PersonLegal created: {}", personLegalDto);

        return ArtistLegalResponse.builder()
                .documentNumber(personLegal != null ? personLegal.getDocumentNumber() : null)
                .companyName(personLegal != null ? personLegal.getCompanyName() : null)
                .fantasyName(personLegal != null ? personLegal.getFantasyName() : null)
                .stateRegistration(personLegal != null ? personLegal.getStateRegistration() : null)
                .municipalRegistration(personLegal != null ? personLegal.getMunicipalRegistration() : null)
                .legalRepresentative(personLegal != null ? personLegal.getLegalRepresentative() : null)
                .legalRepresentativePhone(personLegal != null ? personLegal.getLegalRepresentativePhone() : null)
                .build();
    }

    public ArtistResponse buildArtistResponse(
            String validatedToken,
            PersonResponse personResponse,
            ArtistNaturalResponse artistNaturalResponse,
            ArtistLegalResponse artistLegalResponse) {

        return ArtistResponse.builder()
                .token(validatedToken)
                .personResponse(personResponse)
                .artistNaturalResponse(artistNaturalResponse)
                .artistLegalResponse(artistLegalResponse)
                .build();
    }
}
