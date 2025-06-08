package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.product.Product;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper {

    public Budget toEntity(BudgetDTO dto) {
        return Budget.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .dateBudget(dto.getDateBudget())
                .description(dto.getDescription())
                .response(dto.getResponse())
                .imageUrl(dto.getImageUrl())
                .customer(Customer.builder().id(dto.getCustomer()).build())
                .product(Product.builder().id(dto.getProduct()).build())
                .build();
    }

    public BudgetDTO toDto(Budget budget) {
        return BudgetDTO.builder()
                .id(budget.getId())
                .status(budget.getStatus())
                .dateBudget(budget.getDateBudget())
                .description(budget.getDescription())
                .response(budget.getResponse())
                .imageUrl(budget.getImageUrl())
                .customer(budget.getCustomer() != null ? budget.getCustomer().getId() : null)
                .product(budget.getProduct() != null ? budget.getProduct().getId() : null)
                .build();
    }


}
