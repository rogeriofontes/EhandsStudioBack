package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.budget.BudgetMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetMediaRepository extends JpaRepository<BudgetMedia, Long> {
}
