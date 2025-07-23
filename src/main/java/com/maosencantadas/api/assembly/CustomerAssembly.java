package com.maosencantadas.api.assembly;

import com.maosencantadas.api.dto.CustomerDTO;
import com.maosencantadas.api.dto.UserDTO;
import com.maosencantadas.api.dto.request.CustomerCreateRequest;
import com.maosencantadas.api.dto.request.PersonLegalRequest;
import com.maosencantadas.api.dto.request.PersonNaturalRequest;
import com.maosencantadas.api.dto.request.UserRequest;
import com.maosencantadas.api.dto.response.CustomerLegalResponse;
import com.maosencantadas.api.dto.response.CustomerNaturalResponse;
import com.maosencantadas.api.dto.response.CustomerResponse;
import com.maosencantadas.api.dto.response.PersonResponse;
import com.maosencantadas.api.mapper.CustomerMapper;
import com.maosencantadas.api.mapper.PersonLegalMapper;
import com.maosencantadas.api.mapper.PersonNaturalMapper;
import com.maosencantadas.api.mapper.UserMapper;
import com.maosencantadas.commons.Constants;
import com.maosencantadas.exception.ResourceFoundException;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.customer.Customer;
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
public class CustomerAssembly {

    private final PersonNaturalService personNaturalService;
    private final PersonLegalService personLegalService;
    private final AuthenticationService authenticationService;
    private final CustomerService customerService;
    private final EmailService emailService;
    private final IUserService userService;
    private final UserMapper userMapper;
    private final PersonNaturalMapper personNaturalMapper;
    private final PersonLegalMapper personLegalMapper;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final FF4j ff4j;

    public CustomerResponse create(CustomerCreateRequest customerCreateRequest) {
        UserRequest userRequest = customerCreateRequest.getUser();
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
            throw new ResourceNotFoundException("User ID is null after saving user");
        }

        String validatedToken = authenticationService.getValidatedToken(userSaved);
        log.info("User registered successfully: {}", userSaved);

        String personType = customerCreateRequest.getPersonType();
        PersonNatural personNatural = null;
        CustomerNaturalResponse customerNaturalResponse = null;

        PersonLegal personLegal = null;
        CustomerLegalResponse customerLegalResponse = null;

        if (Constants.PERSON_NATURAL.equalsIgnoreCase(personType)) {
            var personNaturalRequest = customerCreateRequest.getPersonNatural();
            personNatural = processNaturalPerson(personNaturalRequest);
            if (personNatural == null) {
                log.error("Failed to create PersonNatural: {}", personNaturalRequest);
                throw new ResourceNotFoundException("Failed to create PersonNatural: " + personNaturalRequest);
            }

            customerNaturalResponse = processNaturalPersonResponse(personNatural);
            if (customerNaturalResponse == null) {
                log.error("Failed to create CustomerNaturalResponse for person: {}", personNatural.getName());
                throw new ResourceNotFoundException("Failed to create CustomerNaturalResponse for person: " + personNatural.getName());
            }

        } else if (Constants.PERSON_LEGAL.equalsIgnoreCase(personType)) {
            var personLegalRequest = customerCreateRequest.getPersonLegal();
            personLegal = processLegalPerson(personLegalRequest);
            if (personLegal == null) {
                log.error("Failed to create PersonLegal: {}", personLegalRequest);
                throw new ResourceNotFoundException("Failed to create PersonLegal: " + personLegalRequest);
            }

            customerLegalResponse = processLegalPersonResponse(personLegal);
            if (customerLegalResponse == null) {
                log.error("Failed to create PersonLegal: {}", personLegalRequest);
                throw new ResourceNotFoundException("Failed to create CustomerLegalResponse for person: " + personLegal.getName());
            }

        } else {
            log.error("Invalid person type: {}", personType);
            log.error("Failed to create PersonLegal: {}", personType);
            throw new ResourceFoundException("Invalid person type: " + personType);
        }

        CustomerDTO customerDTO = customerCreateRequest.getCustomer();
        if (customerDTO == null) {
            log.error("Customer category ID is required");
            throw new ResourceNotFoundException("Customer category ID is required");
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setUser(user);

        customer.setPerson(personNatural != null ? personNatural : personLegal);

        Customer createdCustomer = customerService.save(customer);
        log.info("Creating customer: {}", createdCustomer);

        CustomerDTO createdCustomerDTO = customerMapper.toDTO(createdCustomer);

        PersonResponse personResponse = buildPerson(createdCustomer, userDTO);

        if (ff4j.check(Constants.SEND_EMAIL)) {
            sendEmail(user.getName(), user.getEmail(), validatedToken);
        }

        log.info("Customer created successfully: {}", createdCustomer.getPerson().getName());
        return buildCustomerResponse(
                createdCustomerDTO.getId(),
                validatedToken,
                personResponse,
                customerNaturalResponse,
                customerLegalResponse
        );
    }

    private PersonResponse buildPerson(Customer customer, UserDTO userDTO) {
        return PersonResponse.builder()
                .id(customer.getPerson().getId())
                .name(customer.getPerson().getName())
                .imageUrl(null)
                .address(customer.getPerson().getAddress())
                .email(customer.getPerson().getEmail())
                .phone(customer.getPerson().getPhone())
                .whatsapp(customer.getPerson().getWhatsapp())
                .categoryId(0L)
                .userId(userDTO.getId())
                .mediaId(customer.getMedia().getId() != null ? customer.getMedia().getId() : null)
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

    private CustomerNaturalResponse processNaturalPersonResponse(PersonNatural personNatural) {
        if (personNatural == null || personNatural.getDocumentNumber() == null || personNatural.getDocumentNumber().isEmpty()) {
            log.error("CPF is required for natural person");
        }

        var personNaturalDto = personNaturalMapper.toDTO(personNatural);
        log.info("PersonNatural created DTO: {}", personNaturalDto);

        if (personNaturalDto == null) {
            log.error("PersonNatural DTO is null for person: {}", personNatural != null ? personNatural.getName() : null);
            return null;
        }

        return CustomerNaturalResponse.builder()
                .cpf(personNatural != null ? personNatural.getDocumentNumber() : null)
                .documentNumber(personNatural != null ? personNatural.getDocumentNumber() : null)
                .identityNumber(personNatural != null ? personNatural.getIdentityNumber() : null)
                .birthDate(personNatural != null ? personNatural.getBirthDate() : null)
                .build();
    }

    private PersonLegal processLegalPerson(PersonLegalRequest personLegalRequest) {
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

    private CustomerLegalResponse processLegalPersonResponse(PersonLegal personLegal) {
        if (personLegal == null || personLegal.getDocumentNumber() == null || personLegal.getDocumentNumber().isEmpty()) {
            log.error("CNPJ is required for legal person");
        }

        var personLegalDto = personLegalMapper.toDTO(personLegal);
        log.info("PersonLegal created: {}", personLegalDto);

        return CustomerLegalResponse.builder()
                .documentNumber(personLegal != null ? personLegal.getDocumentNumber() : null)
                .companyName(personLegal != null ? personLegal.getCompanyName() : null)
                .fantasyName(personLegal != null ? personLegal.getFantasyName() : null)
                .stateRegistration(personLegal != null ? personLegal.getStateRegistration() : null)
                .municipalRegistration(personLegal != null ? personLegal.getMunicipalRegistration() : null)
                .legalRepresentative(personLegal != null ? personLegal.getLegalRepresentative() : null)
                .legalRepresentativePhone(personLegal != null ? personLegal.getLegalRepresentativePhone() : null)
                .build();
    }

    private CustomerResponse buildCustomerResponse(
            Long id,
            String validatedToken,
            PersonResponse personResponse,
            CustomerNaturalResponse customerNaturalResponse,
            CustomerLegalResponse customerLegalResponse) {

        return CustomerResponse.builder()
                .id(id)
                .token(validatedToken)
                .personResponse(personResponse)
                .customerNaturalResponse(customerNaturalResponse)
                .customerLegalResponse(customerLegalResponse)
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
