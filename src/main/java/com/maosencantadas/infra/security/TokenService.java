package com.maosencantadas.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.repository.ArtistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final ArtistRepository artistRepository;

    public TokenService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public String generateToken(User user) {
        try {
            Optional<Artist> artistOptional = artistRepository.findByUser(user);

            var tokenBuilder = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withClaim("userId", user.getId())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(genExpirationDate());

            artistOptional.ifPresent(artist -> tokenBuilder.withClaim("artistId", artist.getPerson().getId()));

            return tokenBuilder.sign(getAlgorithm());
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Algorithm getAlgorithm() {
        log.info("Using secret for HMAC256: {}", secret);
        return Algorithm.HMAC256(Base64.getDecoder().decode(secret));
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateActivationToken() {
        UUID uuid = UUID.randomUUID();
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(uuid.toString().getBytes())
                .substring(0, 8);
    }
}
