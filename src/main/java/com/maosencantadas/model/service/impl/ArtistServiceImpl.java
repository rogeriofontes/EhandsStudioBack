package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.api.dto.UserDTO;
import com.maosencantadas.api.mapper.ArtistMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.domain.user.UserRole;
import com.maosencantadas.model.repository.ArtistRepository;
import com.maosencantadas.model.repository.CategoryRepository;
import com.maosencantadas.model.repository.UserRepository;
import com.maosencantadas.model.service.ArtistService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ArtistDTO> findAllArtists() {
        log.info("Listing all artists");
        return artistRepository.findAll()
                .stream()
                .map(artistMapper::toDTO)
                .toList();
    }

    @Override
    public ArtistDTO findArtistById(Long id) {
        log.info("Finding artist by id: {}", id);
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));
        return artistMapper.toDTO(artist);
    }

    @Override
    public List<ArtistDTO> findArtistsByCategoryName(String categoryName) {
        log.info("Finding artists by category name: {}", categoryName);
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name '" + categoryName + "'"));

        List<Artist> artists = artistRepository.findByCategoryId(category.getId());

        if (artists.isEmpty()) {
            log.warn("No artists found for category '{}'", categoryName);
            throw new ResourceNotFoundException("No artists found for category with name '" + categoryName + "'");
        }

        return artists.stream()
                .map(artistMapper::toDTO)
                .toList();
    }

    @Override
    public List<ArtistDTO> findArtistsByCategoryId(Long categoryId) {
        log.info("Finding artists by category ID: {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            log.warn("Category not found with ID '{}'", categoryId);
            throw new ResourceNotFoundException("Category not found with ID '" + categoryId + "'");
        }

        List<Artist> artists = artistRepository.findByCategoryId(categoryId);

        if (artists.isEmpty()) {
            log.warn("No artists found for category ID '{}'", categoryId);
            throw new ResourceNotFoundException("No artists found for category with ID '" + categoryId + "'");
        }

        return artists.stream()
                .map(artistMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public ArtistDTO saveArtist(ArtistDTO artistDTO) {
        log.info("Saving new artist: {}", artistDTO.getName());

        if (artistDTO.getUser() == null) {
            throw new IllegalArgumentException("User information is required");
        }

        User user = createUserFromDTO(artistDTO.getUser());

        log.debug("Artist User saved with ID: {}", user.getId());

        if (artistDTO.getCategory() == null || artistDTO.getCategory().getId() == null) {
            throw new IllegalArgumentException("Category ID is required");
        }

        Category category = categoryRepository.findById(artistDTO.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + artistDTO.getCategory().getId()));

        log.debug("Artist category found with ID: {}", category.getId());

        Artist artist = new Artist();
        artist.setName(artistDTO.getName());
        artist.setAddress(artistDTO.getAddress());
        artist.setEmail(artistDTO.getEmail());
        artist.setCpf(artistDTO.getCpf());
        artist.setPhone(artistDTO.getPhone());
        artist.setWhatsapp(artistDTO.getWhatsapp());
        artist.setFace(artistDTO.getFace());
        artist.setInsta(artistDTO.getInsta());
        artist.setImageUrl(artistDTO.getImageUrl());
        artist.setCategory(category);
        artist.setUser(user);

        Artist savedArtist = artistRepository.save(artist);
        log.debug("Artist saved with ID: {}", savedArtist.getId());

        return artistMapper.toDTO(savedArtist);
    }

    private User createUserFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(passwordEncoder.encode("senhaTemporaria123"));
        user.setRole(UserRole.ARTIST);
        return userRepository.save(user);
    }

    @Override
    public ArtistDTO updateArtist(Long id, ArtistDTO artistDTO) {
        log.info("Updating artist with id: {}", id);

        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id " + id));

        if (artistDTO.getCategory() == null || artistDTO.getCategory().getId() == null) {
            throw new IllegalArgumentException("Category ID is required for update");
        }

        Category category = categoryRepository.findById(artistDTO.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID '" + artistDTO.getCategory().getId() + "'"));

        User user = userRepository.findById(artistDTO.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + artistDTO.getUser().getId()));

        if (user.getRole() != UserRole.ARTIST) {
            throw new IllegalArgumentException("The user must have ARTIST role");
        }

        artist.setName(artistDTO.getName());
        artist.setAddress(artistDTO.getAddress());
        artist.setEmail(artistDTO.getEmail());
        artist.setPhone(artistDTO.getPhone());
        artist.setInsta(artistDTO.getInsta());
        artist.setFace(artistDTO.getFace());
        artist.setImageUrl(artistDTO.getImageUrl());
        artist.setWhatsapp(artistDTO.getWhatsapp());
        artist.setCpf(artistDTO.getCpf());
        artist.setCategory(category);
        artist.setUser(user);

        Artist updated = artistRepository.save(artist);
        return artistMapper.toDTO(updated);
    }

    @Override
    public void deleteArtist(Long id) {
        log.info("Deleting artist with id: {}", id);
        if (!artistRepository.existsById(id)) {
            log.warn("Attempted to delete non-existent artist with id {}", id);
            throw new ResourceNotFoundException("Artist not found with id " + id);
        }
        artistRepository.deleteById(id);
    }
}
