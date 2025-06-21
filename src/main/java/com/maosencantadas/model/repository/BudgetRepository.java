package com.maosencantadas.model.repository;

import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.model.domain.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findBudgetByCustomerId(Long customerID);
}
