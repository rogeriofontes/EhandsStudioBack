package com.maosencantadas.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.model.domain.budget.Budget;

@Component
public class BudgetMapper {

    private final ModelMapper modelMapper;

    public BudgetMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BudgetDTO toDTO(Budget budget) {
        return modelMapper.map(budget, BudgetDTO.class);
    }

    public Budget toEntity(BudgetDTO budgetDTO) {
        return modelMapper.map(budgetDTO, Budget.class);
    }
}
