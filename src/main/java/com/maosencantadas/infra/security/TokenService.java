package com.maosencantadas.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class TokenService {

    @Value("${api.security.token.secret:my-secret-key}")

    private String secret;

    private final ArtistRepository artistRepository;

    public TokenService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            Optional<Artist> artistOptional = artistRepository.findByUser(user);

            var tokenBuilder = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withClaim("userId", user.getId())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(genExpirationDate());

            artistOptional.ifPresent(artist -> tokenBuilder.withClaim("artistId", artist.getId()));

            return tokenBuilder.sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
