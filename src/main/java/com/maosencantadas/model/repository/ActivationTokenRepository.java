package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.user.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long>  {
}
