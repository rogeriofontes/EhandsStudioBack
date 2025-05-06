package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ImagemDTO;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public abstract interface ImagemService {

    ImagemDTO salvarImagem(ImagemDTO imagemDTO, MultipartFile arquivo);

    ImagemDTO salvarImagem(ImagemDTO dto);

    List<ImagemDTO> buscarPorArtistaId(Long artistaId);

    List<ImagemDTO> buscarPorProdutoId(Long produtoId);

    List<ImagemDTO> buscarPorCategoriaId(Long categoriaId);
}
