package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.api.dto.BudgetResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BudgetService {

    List<BudgetDTO> findAllBudgets();

    BudgetDTO findBudgetById(Long id);

    BudgetDTO createBudget(BudgetDTO dto);

    BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO);

    void deleteBudget(Long id);

    BudgetDTO findBudgetByCustomerId(Long customerID);

    BudgetDTO createBudgetWithoutImage(BudgetDTO request);

    BudgetDTO createBudgetResponse(Long budgetId, BudgetResponseDTO budgetResponseDTO);
}
