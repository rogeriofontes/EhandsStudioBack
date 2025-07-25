package com.maosencantadas.model.service.impl;

import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.budget.QuoteMessage;
import com.maosencantadas.model.repository.BudgetRepository;
import com.maosencantadas.model.repository.QuoteMessageRepository;
import com.maosencantadas.model.service.QuoteMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteMessageServiceImpl implements QuoteMessageService {

    private final QuoteMessageRepository quoteMessageRepository;
    private final BudgetRepository budgetRepository;

    @Override
    public List<QuoteMessage> findAll() {
        log.info("Listing all categories");
        return quoteMessageRepository.findAll();
    }

    @Override
    public QuoteMessage findById(Long id) {
        log.info("Finding category by id: {}", id);
        return quoteMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public QuoteMessage save(QuoteMessage quoteMessage) {
        log.info("Saving new category: {}", quoteMessage.getId());
        Long budgetId = quoteMessage.getBudget().getId();
        if (budgetId == null) {
            throw new IllegalArgumentException("Budget ID must not be null");
        }

        Optional<Budget> byId = budgetRepository.findById(budgetId);
        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("Budget not found with id " + budgetId);
        }

        return quoteMessageRepository.save(quoteMessage);
    }

    @Override
    public QuoteMessage update(Long id, QuoteMessage quoteMessage) {
        log.info("Updating category with id: {}", id);
        return quoteMessageRepository.findById(id)
                .map(existingQuoteMessage -> {
                    existingQuoteMessage.setMessage(quoteMessage.getMessage());
                    existingQuoteMessage.setSentByCustomer(quoteMessage.getSentByCustomer());
                    existingQuoteMessage.setSentAt(quoteMessage.getSentAt());
                    return quoteMessageRepository.save(existingQuoteMessage);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category with id: {}", id);
        if (!quoteMessageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
        quoteMessageRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long quoteMessageId) {
        return quoteMessageRepository.existsById(quoteMessageId);
    }
}
