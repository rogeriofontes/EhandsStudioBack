package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.user.ActivationToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {

    ActivationToken findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE ActivationToken at SET at.active = true, at.lastModifiedBy = 'root@localhost', at.lastModifiedDate = CURRENT_TIMESTAMP WHERE at.user.id = :userId")
    void activateTokenByUserId(@Param("userId") Long userId);

}
