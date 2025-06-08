package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.api.mapper.BudgetMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.repository.BudgetRepository;
import com.maosencantadas.model.repository.CustomerRepository;
import com.maosencantadas.model.repository.ProductRepository;
import com.maosencantadas.model.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public List<BudgetDTO> findAllBudgets() {
        log.info("Listing all budgets");
        List<Budget> budgets = budgetRepository.findAll();
        return budgets.stream()
                .map(budgetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetDTO findBudgetById(Long id) {
        log.info("Finding budget by id: {}", id);
        Optional<Budget> budget = budgetRepository.findById(id);
        if (budget.isEmpty()) {
            log.warn("Budget not found with id: {}", id);
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        return budgetMapper.toDTO(budget.get());
    }

    @Override
    public BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO) {
        log.info("Updating budget with id: {}", id);

        Customer customer = customerRepository.findById(budgetDTO.getCustomer())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + budgetDTO.getCustomer()));

        Product product = productRepository.findById(budgetDTO.getProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + budgetDTO.getProduct()));

        Budget budgetUpdated = budgetRepository.findById(id)
                .map(budget -> {
                    budget.setStatus(budgetDTO.getStatus());
                    budget.setDate_budget(budgetDTO.getDateBudget());
                    budget.setDescription(budgetDTO.getDescription());
                    budget.setResponse(budgetDTO.getResponse());
                    budget.setImageUrl(budgetDTO.getImageUrl());
                    budget.setCustomer(customer);
                    budget.setProduct(product);
                    return budgetRepository.save(budget);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id " + id));
        return budgetMapper.toDTO(budgetUpdated);
    }

    @Override
    public void deleteBudget(Long id) {
        log.info("Deleting budget with id: {}", id);
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        budgetRepository.deleteById(id);
    }
}
