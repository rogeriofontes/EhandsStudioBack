package com.maosencantadas.model.service.impl;

import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.budget.BudgetMedia;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.repository.BudgetMediaRepository;
import com.maosencantadas.model.repository.BudgetRepository;
import com.maosencantadas.model.repository.MediaRepository;
import com.maosencantadas.model.service.BudgetMediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetMediaServiceImpl implements BudgetMediaService {

    private final BudgetMediaRepository budgetMediaRepository;
    private final BudgetRepository budgetRepository;
    private final MediaRepository mediaRepository;

    @Override
    public List<BudgetMedia> findAll() {
        log.info("Listing all budgets");
        return budgetMediaRepository.findAll();
    }

    @Override
    public BudgetMedia create(BudgetMedia budgetMedia) {
        log.info("Creating new budget");

        Long budgetId = budgetMedia.getBudget().getId();
        Optional<Budget> budgetIdOp = budgetRepository.findById(budgetId);
        if (budgetIdOp.isEmpty()) {
            log.error("Budget with ID {} not found", budgetId);
            throw new IllegalArgumentException("Budget not found");
        }

        budgetMedia.setBudget(budgetIdOp.get());

        Long mediaId = budgetMedia.getMedia().getId();
        Optional<Media> mediaIdOp = mediaRepository.findById(mediaId);
        if (mediaIdOp.isEmpty()) {
            log.error("Media with ID {} not found", mediaId);
            throw new IllegalArgumentException("Media not found");
        }

        budgetMedia.setMedia(mediaIdOp.get());

        return budgetMediaRepository.save(budgetMedia);
    }
}
