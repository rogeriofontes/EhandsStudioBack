package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.api.dto.CategoryDTO;
import com.maosencantadas.api.dto.UserDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.domain.user.UserRole;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    private final ModelMapper modelMapper;

    public ArtistMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<Artist, ArtistDTO> typeMap = modelMapper.createTypeMap(Artist.class, ArtistDTO.class);
        typeMap.setPostConverter(context -> {
            Artist source = context.getSource();
            ArtistDTO destination = context.getDestination();

            if (source.getUser() != null) {
                User user = source.getUser();
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setLogin(user.getLogin());
                userDTO.setUserRole(user.getRole().name());
                destination.setUser(userDTO);
            }

            if (source.getCategory() != null) {
                Category category = source.getCategory();
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                destination.setCategory(categoryDTO);
            }

            return destination;
        });

        TypeMap<ArtistDTO, Artist> typeMapReverse = modelMapper.createTypeMap(ArtistDTO.class, Artist.class);
        typeMapReverse.setPostConverter(context -> {
            ArtistDTO source = context.getSource();
            Artist destination = context.getDestination();

            if (source.getUser() != null) {
                UserDTO userDTO = source.getUser();
                User user = new User();
                user.setId(userDTO.getId());
                user.setLogin(userDTO.getLogin());
                if (userDTO.getUserRole() != null) {
                    user.setRole(Enum.valueOf(UserRole.class, userDTO.getUserRole()));
                }
                destination.setUser(user);
            }

            if (source.getCategory() != null) {
                CategoryDTO categoryDTO = source.getCategory();
                Category category = new Category();
                category.setId(categoryDTO.getId());
                category.setName(categoryDTO.getName());
                destination.setCategory(category);
            }

            return destination;
        });
    }

    public ArtistDTO toDTO(Artist artist) {
        return modelMapper.map(artist, ArtistDTO.class);
    }

    public Artist toEntity(ArtistDTO artistDTO) {
        return modelMapper.map(artistDTO, Artist.class);
    }
}
