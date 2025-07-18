package com.maosencantadas.model.service.impl;

import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.artist.ArtistSocialMedia;
import com.maosencantadas.model.repository.ArtistSocialMediaRepository;
import com.maosencantadas.model.service.ArtistSocialMediaService;
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
public class ArtistSocialMediaServiceImpl implements ArtistSocialMediaService {

    private final ArtistSocialMediaRepository artistSocialMediaRepository;
    private final IUserService userService;

    @Override
    public List<ArtistSocialMedia> findAll() {
        log.info("Listing all artists");
        return artistSocialMediaRepository.findAll();
    }

    @Override
    public ArtistSocialMedia findById(Long id) {
        log.info("Finding artist by id: {}", id);
        return artistSocialMediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));
    }

    @Override
    public ArtistSocialMedia findByArtistId(Long artistId) {
        Optional<ArtistSocialMedia> artist = artistSocialMediaRepository.findByArtistId(artistId);
        log.info("Finding artist by user ID: {}", artistId);

        if (artist.isEmpty()) {
            log.warn("No artist found for user ID '{}'", artistId);
            throw new ResourceNotFoundException("No artist found for user with ID '" + artistId + "'");
        }

        return artist.get();
    }

    @Override
    @Transactional
    public ArtistSocialMedia save(ArtistSocialMedia artistSocialMedia) {
        log.debug("Artist saved with ID: {}", artistSocialMedia.getId());
        return artistSocialMediaRepository.save(artistSocialMedia);
    }

    @Override
    public ArtistSocialMedia update(Long id, ArtistSocialMedia artistSocialMedia) {
        log.info("Updating artist with id: {}", id);
        log.info("Updating product with id: {}", id);
        return artistSocialMediaRepository.findById(id)
                .map(existingArtist -> {
                    log.debug("Found existing artist with ID: {}", existingArtist.getId());
                    existingArtist.setArtist(artistSocialMedia.getArtist());
                    existingArtist.setFacebook(artistSocialMedia.getFacebook());
                    existingArtist.setInstagram(artistSocialMedia.getInstagram());
                    existingArtist.setTwitterX(artistSocialMedia.getTwitterX());
                    existingArtist.setYoutube(artistSocialMedia.getYoutube());
                    existingArtist.setTiktok(artistSocialMedia.getTiktok());
                    existingArtist.setWebsite(artistSocialMedia.getWebsite());
                    existingArtist.setPinterest(artistSocialMedia.getPinterest());
                    existingArtist.setTelegram(artistSocialMedia.getTelegram());

                    log.debug("Artist updated with ID: {}", existingArtist.getId());
                    return artistSocialMediaRepository.save(existingArtist);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting artist with id: {}", id);
        if (!artistSocialMediaRepository.existsById(id)) {
            log.warn("Attempted to delete non-existent artist with id {}", id);
            throw new ResourceNotFoundException("Artist not found with id " + id);
        }
        artistSocialMediaRepository.deleteById(id);
    }
}
