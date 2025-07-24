package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.artist.ArtistCategory;
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
public class ArtistMapper {

    private final ModelMapper modelMapper;

    public ArtistMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        configureArtistToArtistDTO();
        configureArtistDTOToArtist();
    }

    private void configureArtistToArtistDTO() {
        TypeMap<Artist, ArtistDTO> typeMap = modelMapper.createTypeMap(Artist.class, ArtistDTO.class);
        typeMap.setPostConverter(context -> {
            try {
                Artist source = context.getSource();
                ArtistDTO destination = context.getDestination();

                // Corrija aqui: pega o ID do próprio Artist!
                destination.setId(source.getId());

                // Mapeie campos vindos de Person
                Person person = source.getPerson();
                if (person != null) {
                    destination.setName(person.getName());
                    destination.setAddress(person.getAddress());
                    destination.setEmail(person.getEmail());
                    destination.setPhone(person.getPhone());
                    destination.setWhatsapp(person.getWhatsapp());
                }

                if (source.getMedia() != null) {
                    destination.setMediaId(source.getMedia().getId());
                }

                if (source.getArtistCategory() != null) {
                    destination.setArtistCategoryId(source.getArtistCategory().getId());
                }

                if (source.getUser() != null) {
                    destination.setUserId(source.getUser().getId());
                }

                return destination;
            } catch (Exception ex) {
                ex.printStackTrace(); // Loga para identificar o problema real
                throw ex; // Relança para o ModelMapper capturar
            }
        });
    }

    private void configureArtistDTOToArtist() {
        TypeMap<ArtistDTO, Artist> typeMapReverse = modelMapper.createTypeMap(ArtistDTO.class, Artist.class);
        typeMapReverse.setPostConverter(context -> {
            ArtistDTO source = context.getSource();
            Artist destination = context.getDestination();

            if (source.getId() != null) {
                User user = new User();
                user.setId(source.getId());
                destination.setUser(user);
            }

            if (source.getMediaId() != null) {
                Media media = new Media();
                media.setId(source.getMediaId());
                destination.setMedia(media);
            }

            if (source.getArtistCategoryId() != null) {
                ArtistCategory artistCategory = new ArtistCategory();
                artistCategory.setId(source.getArtistCategoryId());
                destination.setArtistCategory(artistCategory);
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