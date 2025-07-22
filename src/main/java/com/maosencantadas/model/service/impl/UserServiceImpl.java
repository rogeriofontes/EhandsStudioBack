package com.maosencantadas.model.service.impl;

import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.repository.UserRepository;
import com.maosencantadas.model.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(userRepository.save(user));
    }
}
