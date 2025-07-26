package com.maosencantadas.maosencantadas.controller;

import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistService artistService;

    @Test
    void deveCadastrarArtistaComSucesso() throws Exception {
        // Dados de entrada
        Artist artist = Artist.builder()
                .id(1L)
                .imageUrl("https://testing.com/foto.jpg")
                .build();

        // Mock do serviço
        when(artistService.save(any(Artist.class))).thenReturn(artist);

        // Requisição simulada
        mockMvc.perform(post("/api/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "userId": 1,
                        "imageUrl": "https://testing.com/foto.jpg",
                        "artistCategoryId": 1,
                        "mediaId": 1
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.imageUrl").value("https://testing.com/foto.jpg"));
    }
}
