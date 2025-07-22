package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.UserDTO;
import com.maosencantadas.api.dto.request.UserRequest;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.domain.user.UserRole;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Mapeamento explícito de User -> UserDTO
        modelMapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> {
            mapper.map(User::getRole, UserDTO::setUserRole); // Enum para String
            mapper.map(User::getName, UserDTO::setName);
            mapper.map(User::getEmail, UserDTO::setEmail);
        });

        // Mapeamento explícito de UserDTO -> User
        modelMapper.typeMap(UserDTO.class, User.class).addMappings(mapper -> {
            mapper.map(UserDTO::getName, User::setName);
            mapper.map(UserDTO::getEmail, User::setEmail);
            //mapper.map(dto -> UserRole.valueOf(dto.getUserRole()), User::setRole); // String para Enum
            mapper.map(dto -> toUserRole(dto.getUserRole()), User::setRole);
        });
    }

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public User toEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public List<User> toEntity(List<UserDTO> userDTOs) {
        return userDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<UserDTO> toDTO(List<User> users) {
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private UserRole toUserRole(String value) {
        try {
            return value != null ? UserRole.valueOf(value) : null;
        } catch (IllegalArgumentException e) {
            return null; // ou lançar exceção personalizada
        }
    }

}
