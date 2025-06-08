package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.BudgetDTO;

import java.util.List;

public interface BudgetService {

    List<BudgetDTO> findAllBudgets();

    BudgetDTO findBudgetById(Long id);

    BudgetDTO createBudget(BudgetDTO dto);

    BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO);

    void deleteBudget(Long id);
}
