package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.CustomerDTO;
import com.maosencantadas.api.dto.UserDTO;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.domain.user.UserRole;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<Customer, CustomerDTO> typeMap = modelMapper.createTypeMap(Customer.class, CustomerDTO.class);


        typeMap.setPostConverter(context -> {
            Customer source = context.getSource();
            CustomerDTO destination = context.getDestination();

            if (source.getUser() != null) {
                User user = source.getUser();
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setLogin(user.getLogin());
                userDTO.setUserRole(user.getRole().name());
                destination.setUserId(userDTO.getId());
            }

            return destination;
        });

        TypeMap<CustomerDTO, Customer> typeMapReverse = modelMapper.createTypeMap(CustomerDTO.class, Customer.class);
        typeMapReverse.setPostConverter(context -> {
            CustomerDTO source = context.getSource();
            Customer destination = context.getDestination();

            if (source.getUserId() != null) {
                Long userId = source.getUserId();
                // Assuming you have a method to fetch UserDTO by ID
                UserDTO userDTO = new UserDTO();
                userDTO.setId(userId);
            }

            return destination;
        });
    }

    public CustomerDTO toDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}
