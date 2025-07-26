package com.maosencantadas.maosencantadas.service;

import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.domain.user.UserRole;
import com.maosencantadas.model.repository.ArtistRepository;
import com.maosencantadas.model.repository.UserRepository;
import com.maosencantadas.model.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ArtistService artistService;

    @Test
    void deveCadastrarArtistaComSucesso() {
        // Mock dos dados
        User user = User.builder()
                .id(1L)
                .name("Amanda Smith")
                .email("ds@msk.com")
                .password("senha123")
                .role(UserRole.ARTIST)
                .build();

        Artist artist = new Artist(1L, user, null, "https://testing.com/foto.jpg", null, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(artistRepository.save(any(Artist.class))).thenReturn(artist);

        Artist resultado = artistService.save(artist);

        // Verificações
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("https://testing.com/foto.jpg", resultado.getImageUrl());
    }
}