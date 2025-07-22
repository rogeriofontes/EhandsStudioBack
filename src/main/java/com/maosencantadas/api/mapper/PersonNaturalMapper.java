package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.request.PersonNaturalRequest;
import com.maosencantadas.model.domain.person.PersonNatural;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonNaturalMapper {

    private final ModelMapper modelMapper;

    public PersonNaturalMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);

        configurePersonNaturalToPersonNaturalDTO();
        configurePersonNaturalDTOToPersonNatural();
    }

    private void configurePersonNaturalToPersonNaturalDTO() {
        // Define the mapping from PersonNatural to PersonNaturalDTO
        modelMapper.createTypeMap(PersonNaturalMapper.class, PersonNaturalRequest.class)
                .setPostConverter(context -> {
                    PersonNaturalMapper source = context.getSource();
                    PersonNaturalRequest destination = context.getDestination();

                    // Custom mapping logic if needed
                    return destination;
                });
    }

    private void configurePersonNaturalDTOToPersonNatural() {
        // Define the mapping from PersonNaturalDTO to PersonNatural
        modelMapper.createTypeMap(PersonNaturalRequest.class, PersonNaturalMapper.class)
                .setPostConverter(context -> {
                    PersonNaturalRequest source = context.getSource();
                    PersonNaturalMapper destination = context.getDestination();

                    // Custom mapping logic if needed
                    return destination;
                });
    }

    public PersonNaturalRequest toDTO(PersonNatural personNatural) {
        return modelMapper.map(personNatural, PersonNaturalRequest.class);
    }

    public PersonNatural toEntity(PersonNaturalRequest personNaturalRequest) {
        return modelMapper.map(personNaturalRequest, PersonNatural.class);
    }

    public List<PersonNaturalRequest> toDTO(List<PersonNatural> personNaturals) {
        return personNaturals.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
