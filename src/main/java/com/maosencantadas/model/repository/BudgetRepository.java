package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maosencantadas.model.domain.budget.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
