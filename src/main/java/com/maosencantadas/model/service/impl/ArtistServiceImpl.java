package com.maosencantadas.model.service.impl;

import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.domain.user.UserRole;
import com.maosencantadas.model.repository.ArtistRepository;
import com.maosencantadas.model.service.ArtistService;
import com.maosencantadas.model.service.CategoryService;
import com.maosencantadas.model.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final CategoryService categoryService;
    private final IUserService userService;

    @Override
    public List<Artist> findAll() {
        log.info("Listing all artists");
        return artistRepository.findAll();
    }

    @Override
    public Artist findById(Long id) {
        log.info("Finding artist by id: {}", id);
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));
    }

    @Override
    public List<Artist> findByCategoryName(String categoryName) {
        log.info("Finding artists by category name: {}", categoryName);
        Category category = categoryService.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name '" + categoryName + "'"));

        return artistRepository.findByCategoryId(category.getId());
    }

    @Override
    public Artist findByUserId(Long userId) {
        Optional<Artist> artist = artistRepository.findByUserId(userId);
        log.info("Finding artist by user ID: {}", userId);

        if (artist.isEmpty()) {
            log.warn("No artist found for user ID '{}'", userId);
            throw new ResourceNotFoundException("No artist found for user with ID '" + userId + "'");
        }

        return artist.get();
    }

    @Override
    public List<Artist> findByCategoryId(Long categoryId) {
        log.info("Finding artists by category ID: {}", categoryId);

        if (!categoryService.existsById(categoryId)) {
            log.warn("Category not found with ID '{}'", categoryId);
            throw new ResourceNotFoundException("Category not found with ID '" + categoryId + "'");
        }

        return artistRepository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional
    public Artist save(Artist artist) {
        log.info("Saving new artist: {}", artist.getName());
        if (artist.getUser() == null || artist.getUser().getId() == null) {
            throw new IllegalArgumentException("User information is required to save an artist");
        }

        Long userId = artist.getUser().getId();
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            log.debug("User with ID {} already exists, using existing user", userId);

            if (!user.get().getRole().equals(UserRole.ARTIST)) {
                throw new IllegalArgumentException("The user must have CUSTOMER role");
            }

            Artist savedArtist = artistRepository.save(artist);
            log.debug("Artist saved with ID: {}", savedArtist.getId());

            return savedArtist;
        }

        return null;
    }

    @Override
    public Artist update(Long id, Artist artist) {
        log.info("Updating artist with id: {}", id);
        log.info("Updating product with id: {}", id);
        return artistRepository.findById(id)
                .map(existingArtist -> {
                    existingArtist.setName(artist.getName());
                    existingArtist.setImageUrl(artist.getImageUrl());
                    existingArtist.setAddress(artist.getAddress());
                    existingArtist.setEmail(artist.getEmail());
                    existingArtist.setPhone(artist.getPhone());
                    existingArtist.setFace(artist.getFace());
                    existingArtist.setInsta(artist.getInsta());
                    existingArtist.setWhatsapp(artist.getWhatsapp());
                    existingArtist.setCpf(artist.getCpf());
                    existingArtist.setCategory(artist.getCategory());
                    existingArtist.setMedia(artist.getMedia());
                    existingArtist.setUser(artist.getUser());

                    log.debug("Artist updated with ID: {}", existingArtist.getId());
                    return artistRepository.save(existingArtist);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting artist with id: {}", id);
        if (!artistRepository.existsById(id)) {
            log.warn("Attempted to delete non-existent artist with id {}", id);
            throw new ResourceNotFoundException("Artist not found with id " + id);
        }
        artistRepository.deleteById(id);
    }
}
