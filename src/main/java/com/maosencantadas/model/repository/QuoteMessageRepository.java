package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.budget.QuoteMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteMessageRepository extends JpaRepository<QuoteMessage, Long> {
}
