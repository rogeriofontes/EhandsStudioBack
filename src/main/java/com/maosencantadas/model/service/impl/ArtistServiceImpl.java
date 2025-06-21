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
import java.util.Optional;

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
    public ArtistDTO findArtistByUserId(Long userId) {
        Optional<Artist> artist = artistRepository.findByUserId(userId);
        log.info("Finding artist by user ID: {}", userId);

        if (artist.isEmpty()) {
            log.warn("No artist found for user ID '{}'", userId);
            throw new ResourceNotFoundException("No artist found for user with ID '" + userId + "'");
        }

        return artistMapper.toDTO(artist.get());
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

        if (artistDTO.getUserId() == null) {
            throw new IllegalArgumentException("User information is required");
        }

        User user = createUserFromUserId(artistDTO.getUserId());
        log.debug("Artist User saved with ID: {}", user.getId());

        Category category = createCategoryFromCategoryId(artistDTO.getCategoryId());
        log.debug("Artist category found with ID: {}", category.getId());

        Artist artist = Artist.builder()
                .name(artistDTO.getName())
                .address(artistDTO.getAddress())
                .email(artistDTO.getEmail())
                .cpf(artistDTO.getCpf())
                .phone(artistDTO.getPhone())
                .whatsapp(artistDTO.getWhatsapp())
                .face(artistDTO.getFace())
                .insta(artistDTO.getInsta())
                .imageUrl(artistDTO.getImageUrl())
                .category(category)
                .user(user)
                .build();

        Artist savedArtist = artistRepository.save(artist);
        log.debug("Artist saved with ID: {}", savedArtist.getId());

        return artistMapper.toDTO(savedArtist);
    }

    private Category createCategoryFromCategoryId(Long categoryId) {
        log.info("Creating user for artist with category ID: {}", categoryId);

        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            log.debug("Category found with ID: {}", category.get().getId());
            return category.get();
        } else {
            log.warn("Category not found with ID: {}", category);
            throw new EntityNotFoundException("User not found with ID: " + categoryId);
        }
    }

    private User createUserFromUserId(Long userId) {
        log.info("Creating user for artist with user ID: {}", userId);

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            log.debug("User found with ID: {}", user.get().getId());
            return user.get();
        } else {
            log.warn("User not found with ID: {}", userId);
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public ArtistDTO updateArtist(Long id, ArtistDTO artistDTO) {
        log.info("Updating artist with id: {}", id);

        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id " + id));

        if (artistDTO.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID is required for update");
        }

        User user = createUserFromUserId(artistDTO.getUserId());
        log.debug("Artist User found for updated with ID: {}", user.getId());

        Category category = createCategoryFromCategoryId(artistDTO.getUserId());
        log.debug("Artist category found for updated  with ID: {}", category.getId());

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
