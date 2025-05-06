package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.ImagemDTO;
import com.maosencantadas.model.domain.artista.Artista;
import com.maosencantadas.model.domain.produto.Produto;
import com.maosencantadas.model.domain.imagem.Imagem;
import com.maosencantadas.model.repository.ArtistaRepository;
import com.maosencantadas.model.repository.ProdutoRepository;
import com.maosencantadas.model.repository.ImagemRepository;
import com.maosencantadas.model.service.ImagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImagemServiceImpl implements ImagemService {

    private final ImagemRepository imagemRepository;
    private final ArtistaRepository artistaRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    public ImagemDTO salvarImagem(ImagemDTO imagemDTO, MultipartFile arquivo) {
        return null;
    }

    @Override
    public ImagemDTO salvarImagem(ImagemDTO dto) {
        Imagem imagem = new Imagem();
        imagem.setNome(dto.getNome());
        imagem.setPasta(dto.getPasta());

        if (dto.getArtistaId() != null) {
            Artista artista = artistaRepository.findById(dto.getArtistaId())
                    .orElseThrow(() -> new RuntimeException("Artista não encontrado"));
            imagem.setArtista(artista);

            String caminhoImagem = dto.getPasta() + "/" + dto.getNome();
            artista.setFoto(caminhoImagem);
            artistaRepository.save(artista);
        }

        if (dto.getProdutoId() != null) {
            Produto produto = produtoRepository.findById(dto.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            imagem.setProduto(produto);

            String caminhoImagem = dto.getPasta() + "/" + dto.getNome();
            produto.setImagemUrl(caminhoImagem);
            produtoRepository.save(produto);
        }

        Imagem imagemSalva = imagemRepository.save(imagem);

        ImagemDTO resposta = new ImagemDTO();
        resposta.setId(imagemSalva.getId());
        resposta.setNome(imagemSalva.getNome());
        resposta.setPasta(imagemSalva.getPasta());
        resposta.setArtistaId(dto.getArtistaId());
        resposta.setProdutoId(dto.getProdutoId());

        return resposta;
    }

    // Agora retornando listas de DTOs
    @Override
    public List<ImagemDTO> buscarPorArtistaId(Long artistaId) {
        return imagemRepository.findByArtistaId(artistaId).stream()
                .map(imagem -> {
                    ImagemDTO dto = new ImagemDTO();
                    dto.setId(imagem.getId());
                    dto.setNome(imagem.getNome());
                    dto.setPasta(imagem.getPasta());
                    dto.setArtistaId(artistaId);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ImagemDTO> buscarPorProdutoId(Long produtoId) {
        return imagemRepository.findByProdutoId(produtoId).stream()
                .map(imagem -> {
                    ImagemDTO dto = new ImagemDTO();
                    dto.setId(imagem.getId());
                    dto.setNome(imagem.getNome());
                    dto.setPasta(imagem.getPasta());
                    dto.setProdutoId(produtoId);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ImagemDTO> buscarPorCategoriaId(Long categoriaId) {
        return imagemRepository.findByCategoriaId(categoriaId).stream()
                .map(imagem -> {
                    ImagemDTO dto = new ImagemDTO();
                    dto.setId(imagem.getId());
                    dto.setNome(imagem.getNome());
                    dto.setPasta(imagem.getPasta());
                    dto.setCategoriaId(categoriaId);
                    return dto;
                }).collect(Collectors.toList());
    }
}
