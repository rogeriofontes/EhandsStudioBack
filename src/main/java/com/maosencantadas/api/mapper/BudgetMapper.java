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
                .budgetStatus(dto.getStatus())
                .dateBudget(dto.getDateBudget())
                .description(dto.getDescription())
                .response(dto.getResponse())
                .imageUrl(dto.getImageUrl())
                .customer(Customer.builder().id(dto.getCustomerId()).build())
                .product(Product.builder().id(dto.getProductId()).build())
                .build();
    }

    public BudgetDTO toDto(Budget budget) {
        return BudgetDTO.builder()
                .id(budget.getId())
                .status(budget.getBudgetStatus())
                .dateBudget(budget.getDateBudget())
                .description(budget.getDescription())
                .response(budget.getResponse())
                .imageUrl(budget.getImageUrl())
                .customerId(budget.getCustomer() != null ? budget.getCustomer().getId() : null)
                .productId(budget.getProduct() != null ? budget.getProduct().getId() : null)
                .artistId(budget.getArtist() != null ? budget.getArtist().getId() : null)
                .build();
    }


}
