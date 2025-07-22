package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.request.PersonLegalRequest;
import com.maosencantadas.model.domain.person.PersonLegal;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonLegalMapper {

    private final ModelMapper modelMapper;

    public PersonLegalMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);

        configurePersonLegalToPersonLegalDTO();
        configurePersonLegalDTOToPersonLegal();
    }

    private void configurePersonLegalToPersonLegalDTO() {
        // Define the mapping from PersonLegal to PersonLegalDTO
        modelMapper.createTypeMap(PersonLegal.class, PersonLegalRequest.class)
                .setPostConverter(context -> {
                    PersonLegal source = context.getSource();
                    PersonLegalRequest destination = context.getDestination();
                    // Custom mapping logic if needed
                    return destination;
                });
    }

    private void configurePersonLegalDTOToPersonLegal() {
        // Define the mapping from PersonLegalDTO to PersonLegal
        modelMapper.createTypeMap(PersonLegalRequest.class, PersonLegal.class)
                .setPostConverter(context -> {
                    PersonLegalRequest source = context.getSource();
                    PersonLegal destination = context.getDestination();

                    // Custom mapping logic if needed
                    return destination;
                });
    }

    public PersonLegalRequest toDTO(PersonLegal personLegal) {
        return modelMapper.map(personLegal, PersonLegalRequest.class);
    }

    public PersonLegal toEntity(PersonLegalRequest personLegalRequest) {
        return modelMapper.map(personLegalRequest, PersonLegal.class);
    }

    public List<PersonLegalRequest> toDTO(List<PersonLegal> personLegals) {
        return personLegals.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PersonLegal> toEntity(List<PersonLegalRequest> personLegalRequests) {
        return personLegalRequests.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
