package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.BudgetResponseDTO;
import com.maosencantadas.api.mapper.BudgetMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.budget.BudgetStatus;
import com.maosencantadas.model.repository.BudgetRepository;
import com.maosencantadas.model.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    @Override
    public List<Budget> findAll() {
        log.info("Listing all budgets");
        return budgetRepository.findAll();
    }

    @Override
    public Budget findById(Long id) {
        log.info("Finding budget by id: {}", id);
        return budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
    }

    @Override
    public Budget create(Budget budget) {
        log.info("Creating new budget");
        return budgetRepository.save(budget);
    }

    @Override
    public Budget update(Long id, Budget budget) {
        log.info("Updating budget with id: {}", id);
        return budgetRepository.findById(id)
                .map(existingBudget -> {
                    existingBudget.setBudgetStatus(budget.getBudgetStatus());
                    existingBudget.setDateBudget(budget.getDateBudget());
                    existingBudget.setDescription(budget.getDescription());
                    existingBudget.setResponse(budget.getResponse());
                    existingBudget.setImageUrl(budget.getImageUrl());
                    existingBudget.setCustomer(budget.getCustomer());
                    existingBudget.setProduct(budget.getProduct());
                    existingBudget.setArtist(budget.getArtist());
                    existingBudget.setMedia(budget.getMedia());
                    return budgetRepository.save(existingBudget);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
    }

    @Override
    public Budget createResponse(Long budgetId, BudgetResponseDTO budgetResponseDTO) {
        Optional<Budget> budget = budgetRepository.findById(budgetId);
        if (budget.isEmpty()) {
            log.warn("Budget for record resonse, not found with id: {}", budgetId);
            throw new ResourceNotFoundException("Budget not found with id: " + budgetId);
        }

        Budget existingBudget = budget.get();
        existingBudget.setBudgetStatus(BudgetStatus.COMPLETED.name());
        existingBudget.setResponse(budgetResponseDTO.getResponse());
        return budgetRepository.save(existingBudget);
    }

    @Override
    public Budget findByCustomerId(Long customerId) {
        Optional<Budget> budgetByCustomerId = budgetRepository.findBudgetByCustomerId(customerId);
        if (budgetByCustomerId.isEmpty()) {
            log.warn("Budget not found for customer with id: {}", customerId);
            throw new ResourceNotFoundException("Budget not found for customer with id: " + customerId);
        }
        log.info("Budget found for customer with id: {}", customerId);
        return budgetByCustomerId.get();
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting budget with id: {}", id);
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        budgetRepository.deleteById(id);
    }
}
