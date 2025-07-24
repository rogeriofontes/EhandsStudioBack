package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.CustomerDTO;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.person.Person;
import com.maosencantadas.model.domain.user.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
            try {
                Customer source = context.getSource();
                CustomerDTO destination = context.getDestination();

                destination.setId(source.getId());

                Person person = source.getPerson();
                if (person != null) {
                    destination.setName(person.getName());
                    destination.setAddress(person.getAddress());
                    destination.setEmail(person.getEmail());
                    destination.setPhone(person.getPhone());
                    destination.setWhatsapp(person.getWhatsapp());
                }

                if (source.getUser() != null) {
                    destination.setUserId(source.getUser().getId());
                }

                if (source.getMedia() != null) {
                    destination.setMediaId(source.getMedia().getId());
                }

                return destination;
            } catch (Exception e) {
                e.printStackTrace(); // Veja o erro no log/console
                throw e;
            }
        });

        TypeMap<CustomerDTO, Customer> typeMapReverse = modelMapper.createTypeMap(CustomerDTO.class, Customer.class);
        typeMapReverse.setPostConverter(context -> {
            CustomerDTO source = context.getSource();
            Customer destination = context.getDestination();

            if (source.getUserId() != null) {
                Long userId = source.getUserId();
                // Assuming you have a method to fetch UserDTO by ID
                User user = new User();
                user.setId(userId);
                destination.setUser(user);
            }

            if (source.getMediaId() != null) {
                Media media = new Media();
                media.setId(source.getMediaId());
                destination.setMedia(media);
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

    public List<CustomerDTO> toDTO(List<Customer> customers) {
        return customers.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Customer> toEntity(List<CustomerDTO> customerDTOs) {
        return customerDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
