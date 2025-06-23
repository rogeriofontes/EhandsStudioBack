package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.media.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long>{
}
