package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.BudgetResponseDTO;
import com.maosencantadas.model.domain.budget.Budget;

import java.util.List;

public interface BudgetService {

    List<Budget> findAll();

    Budget findById(Long id);

    Budget create(Budget budget);

    Budget update(Long id, Budget budget);

    void delete(Long id);

    Budget findByCustomerId(Long customerId);

    Budget createResponse(Long budgetId, BudgetResponseDTO budgetResponseDTO);

    void acceptBudget(Long budgetId);
}
