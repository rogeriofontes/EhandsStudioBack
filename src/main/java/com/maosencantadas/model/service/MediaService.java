package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.MediaDTO;
import com.maosencantadas.model.domain.media.Media;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    MediaDTO save(MultipartFile file);

    Media findById(Long mediaId);
}
