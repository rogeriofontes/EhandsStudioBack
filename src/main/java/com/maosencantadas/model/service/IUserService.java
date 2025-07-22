package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.user.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long userId);
    Optional<User> save(User user);
}
