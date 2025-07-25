package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.budget.BudgetMedia;

import java.util.List;

public interface BudgetMediaService {

    List<BudgetMedia> findAll();

    BudgetMedia create(BudgetMedia budgetMedia);
}
