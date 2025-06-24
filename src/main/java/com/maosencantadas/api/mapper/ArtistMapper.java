package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.api.dto.CategoryDTO;
import com.maosencantadas.api.dto.UserDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.domain.user.UserRole;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
                destination.setUserId(userDTO.getId());
            }

            if (source.getCategory() != null) {
                Category category = source.getCategory();
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                destination.setCategoryId(categoryDTO.getId());
            }

            if (source.getMedia() != null) {
                destination.setMediaId(source.getMedia().getId());
            }

            return destination;
        });

        TypeMap<ArtistDTO, Artist> typeMapReverse = modelMapper.createTypeMap(ArtistDTO.class, Artist.class);
        typeMapReverse.setPostConverter(context -> {
            ArtistDTO source = context.getSource();
            Artist destination = context.getDestination();

            if (source.getUserId() != null) {
                Long userId = source.getUserId();
                User user = new User();
                user.setId(userId);
                destination.setUser(user);
            }

            if (source.getCategoryId() != null) {
                Long categoryId = source.getCategoryId();
                Category category = new Category();
                category.setId(categoryId);
                destination.setCategory(category);
            }

            if (source.getMediaId() != null) {
                Media media = new Media();
                media.setId(source.getMediaId());
                destination.setMedia(media);
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

    public List<Artist> toEntity(List<ArtistDTO> artistDTOs) {
        return artistDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<ArtistDTO> toDTO(List<Artist> artists) {
        return artists.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
