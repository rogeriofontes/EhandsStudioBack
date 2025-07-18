package com.maosencantadas.model.service.impl;

import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.artist.ArtistCategory;
import com.maosencantadas.model.repository.ArtistCategoryRepository;
import com.maosencantadas.model.service.ArtistCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistCategoryServiceImpl implements ArtistCategoryService {

    private final ArtistCategoryRepository artistCategoryRepository;

    @Override
    public List<ArtistCategory> findAll() {
        log.info("Listing all categories");
        return artistCategoryRepository.findAll();
    }

    @Override
    public ArtistCategory findById(Long id) {
        log.info("Finding category by id: {}", id);
        return artistCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public ArtistCategory save(ArtistCategory artistCategory) {
        log.info("Saving new category: {}", artistCategory.getName());
        return artistCategoryRepository.save(artistCategory);
    }

    @Override
    public ArtistCategory update(Long id, ArtistCategory artistCategory) {
        log.info("Updating category with id: {}", id);
        return artistCategoryRepository.findById(id)
                .map(categoryR -> {
                    categoryR.setName(artistCategory.getName());
                    return artistCategoryRepository.save(artistCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category with id: {}", id);
        if (!artistCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
        artistCategoryRepository.deleteById(id);
    }

    @Override
    public Optional<ArtistCategory> findByName(String categoryName) {
        return artistCategoryRepository.findByName(categoryName);
    }

    @Override
    public boolean existsById(Long artistCategoryId) {
        return artistCategoryRepository.existsById(artistCategoryId);
    }
}
