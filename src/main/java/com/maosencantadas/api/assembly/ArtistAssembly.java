package com.maosencantadas.api.assembly;

import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.api.dto.UserDTO;
import com.maosencantadas.api.dto.request.ArtistCreateRequest;
import com.maosencantadas.api.dto.request.PersonLegalRequest;
import com.maosencantadas.api.dto.request.PersonNaturalRequest;
import com.maosencantadas.api.dto.request.UserRequest;
import com.maosencantadas.api.dto.response.ArtistLegalResponse;
import com.maosencantadas.api.dto.response.ArtistNaturalResponse;
import com.maosencantadas.api.dto.response.ArtistResponse;
import com.maosencantadas.api.dto.response.PersonResponse;
import com.maosencantadas.api.mapper.ArtistMapper;
import com.maosencantadas.api.mapper.PersonLegalMapper;
import com.maosencantadas.api.mapper.PersonNaturalMapper;
import com.maosencantadas.api.mapper.UserMapper;
import com.maosencantadas.commons.Constants;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.person.PersonLegal;
import com.maosencantadas.model.domain.person.PersonNatural;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ff4j.FF4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtistAssembly {

    private final PersonNaturalService personNaturalService;
    private final PersonLegalService personLegalService;
    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final ArtistService artistService;
    private final IUserService userService;
    private final UserMapper userMapper;
    private final PersonNaturalMapper personNaturalMapper;
    private final PersonLegalMapper personLegalMapper;
    private final ArtistMapper artistMapper;
    private final PasswordEncoder passwordEncoder;
    private final FF4j ff4j;

    public ArtistResponse create(ArtistCreateRequest artistCreateRequest) {
        UserRequest userRequest = artistCreateRequest.getUser();
        if (isUserRequestInvalid(userRequest)) {
            log.error("Invalid user request: {}", userRequest);
            throw new ResourceNotFoundException("User request is invalid. Email and role are required.");
        }

        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var userSaved = userService.save(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create user"));

        log.info("User created: {}", user.getName());
        UserDTO userDTO = userMapper.toDTO(userSaved);

        if (userDTO.getId() == null) {
            log.error("User ID is null after saving user: {}", userDTO);
            throw new ResourceNotFoundException("User ID is null after saving user. Please check the user creation process.");
        }

        String validatedToken = authenticationService.getValidatedToken(userSaved);
        log.info("User registered successfully: {}", userSaved);

        String personType = artistCreateRequest.getPersonType();
        PersonNatural personNatural = null;
        ArtistNaturalResponse artistNaturalResponse = null;

        PersonLegal personLegal = null;
        ArtistLegalResponse artistLegalResponse = null;

        if (Constants.PERSON_NATURAL.equalsIgnoreCase(personType)) {
            var personNaturalRequest = artistCreateRequest.getPersonNatural();
            personNatural = processNaturalPerson(personNaturalRequest);
            if (personNatural == null) {
                log.error("Failed to create PersonNatural: {}", personNaturalRequest);
                throw new ResourceNotFoundException("Failed to create PersonNatural. Please check the request data.");
            }

            artistNaturalResponse = processNaturalPersonResponse(personNatural);
            if (artistNaturalResponse == null) {
               log.error("Failed to create ArtistNaturalResponse for person: {}", personNatural.getName());
                throw new ResourceNotFoundException("Failed to create ArtistNaturalResponse. Please check the person data.");
            }

        } else if (Constants.PERSON_LEGAL.equalsIgnoreCase(personType)) {
            var personLegalRequest = artistCreateRequest.getPersonLegal();
            personLegal = processLegalPerson(personLegalRequest);
            if (personLegal == null) {
                log.error("Failed to create PersonLegal: {}", personLegalRequest);
                throw new ResourceNotFoundException("Failed to create PersonLegal. Please check the request data.");
            }

            artistLegalResponse = processLegalPersonResponse(personLegal);
            if (artistLegalResponse == null) {
                log.error("Failed to create PersonLegal: {}", personLegalRequest);
                throw new ResourceNotFoundException("Failed to create ArtistLegalResponse. Please check the person data.");
            }

        } else {
            log.error("Invalid person type: {}", personType);
            log.error("Failed to create PersonLegal: {}", personType);
            throw new ResourceNotFoundException("Invalid person type. Expected 'natural' or 'legal'.");
        }

        ArtistDTO artistDTO = artistCreateRequest.getArtist();
        if (artistDTO == null || artistDTO.getArtistCategoryId() == null) {
            log.error("Artist category ID is required");
        }

        Artist artist = artistMapper.toEntity(artistDTO);
        artist.setUser(user);

        artist.setPerson(personNatural != null ? personNatural : personLegal);

        Artist createdArtist = artistService.save(artist);
        log.info("Creating artist: {}", createdArtist);

        ArtistDTO createdArtistDTO = artistMapper.toDTO(createdArtist);

        PersonResponse personResponse = buildPerson(createdArtist, userDTO);

        if (ff4j.check(Constants.SEND_EMAIL)) {
            sendEmail(user.getName(), user.getEmail(), validatedToken);
        }
        return buildArtistResponse(
                createdArtistDTO.getId(),
                validatedToken,
                personResponse,
                artistNaturalResponse,
                artistLegalResponse
        );
    }

    private PersonResponse buildPerson(Artist artist, UserDTO userDTO) {
        return PersonResponse.builder()
                .id(artist.getPerson().getId())
                .name(artist.getPerson().getName())
                .imageUrl(artist.getImageUrl())
                .address(artist.getPerson().getAddress())
                .email(artist.getPerson().getEmail())
                .phone(artist.getPerson().getPhone())
                .whatsapp(artist.getPerson().getWhatsapp())
                .categoryId(artist.getArtistCategory().getId())
                .userId(userDTO.getId())
                .mediaId(artist.getMedia().getId() != null ? artist.getMedia().getId() : null)
                .build();
    }

    private boolean isUserRequestInvalid(UserRequest userRequest) {
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

    private ArtistResponse buildArtistResponse(
            Long id,
            String validatedToken,
            PersonResponse personResponse,
            ArtistNaturalResponse artistNaturalResponse,
            ArtistLegalResponse artistLegalResponse) {

        return ArtistResponse.builder()
                .id(id)
                .token(validatedToken)
                .personResponse(personResponse)
                .artistNaturalResponse(artistNaturalResponse)
                .artistLegalResponse(artistLegalResponse)
                .build();
    }

    private void sendEmail(String name, String email, String validatedToken) {
        try {
            emailService.publish(name, email, "Bem-vindo ao Mãos encantadas!", true, validatedToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
