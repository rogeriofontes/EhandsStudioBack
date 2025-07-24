package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.BudgetResponseDTO;
import com.maosencantadas.exception.AccessDeniedException;
import com.maosencantadas.exception.BusinessException;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.budget.BudgetStatus;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.repository.BudgetRepository;
import com.maosencantadas.model.repository.UserRepository;
import com.maosencantadas.model.service.BudgetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

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
        existingBudget.setBudgetStatus(BudgetStatus.COMPLETED);
        existingBudget.setResponse(budgetResponseDTO.getResponse());
        return budgetRepository.save(existingBudget);
    }

    @Transactional
    public void acceptBudget(Long budgetId) {

        //1. Pegue o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        Optional<User> byEmail = userRepository.findByEmail(currentUserEmail);
        if (byEmail.isEmpty()) {
            log.warn("Usuário não encontrado com email: {}", currentUserEmail);
            throw new ResourceNotFoundException("Usuário não encontrado com email: " + currentUserEmail);
        }

        User user = byEmail.get();
        Long userId = user.getId();
        log.info("Usuário autenticado: {}", userId);

        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Orçamento não encontrado"));

        // 3. Cheque se o usuário autenticado é o mesmo que criou o orçamento
        Long budgetUserId = budget.getCustomer().getUser().getId(); // ajuste conforme seu modelo
        log.info("Usuário que fez o bucket: {}", budgetUserId);

        if (!userId.equals(budgetUserId)) {
            throw new AccessDeniedException("Você não tem permissão para aceitar este orçamento.");
        }

        if (!budget.getBudgetStatus().equals(BudgetStatus.COMPLETED)) {
            throw new BusinessException("Só é possível aceitar orçamentos respondidos pelo artista");
        }

        budget.setBudgetStatus(BudgetStatus.APPROVED);
        // Se quiser, registre data de aceite, usuário que aceitou, etc.
        budgetRepository.save(budget);
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
