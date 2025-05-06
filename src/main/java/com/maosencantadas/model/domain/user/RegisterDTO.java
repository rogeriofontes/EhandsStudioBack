package com.maosencantadas.model.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
