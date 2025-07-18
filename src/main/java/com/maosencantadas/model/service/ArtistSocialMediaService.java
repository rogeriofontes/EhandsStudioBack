package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.artist.ArtistSocialMedia;

import java.util.List;

public interface ArtistSocialMediaService {

    List<ArtistSocialMedia> findAll();

    ArtistSocialMedia findById(Long id);

    ArtistSocialMedia findByArtistId(Long artistId);

    ArtistSocialMedia save(ArtistSocialMedia artistSocialMedia);

    ArtistSocialMedia update(Long id, ArtistSocialMedia artistSocialMedia);

    void delete(Long id);
}
