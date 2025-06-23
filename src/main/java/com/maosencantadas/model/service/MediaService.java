package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.MediaDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    MediaDTO save(MultipartFile file);

}
