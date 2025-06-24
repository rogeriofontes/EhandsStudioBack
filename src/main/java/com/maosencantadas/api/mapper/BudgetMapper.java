package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.product.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetMapper {

    private final ModelMapper modelMapper;

    public BudgetMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Budget → BudgetDTO
        TypeMap<Budget, BudgetDTO> toDTOTypeMap = modelMapper.createTypeMap(Budget.class, BudgetDTO.class);
        toDTOTypeMap.setPostConverter(context -> {
            Budget source = context.getSource();
            BudgetDTO destination = context.getDestination();

            if (source.getMedia() != null) {
                destination.setMediaId(source.getMedia().getId());
            }

            return destination;
        });

        // BudgetDTO → Budget
        TypeMap<BudgetDTO, Budget> toEntityTypeMap = modelMapper.createTypeMap(BudgetDTO.class, Budget.class);
        toEntityTypeMap.setPostConverter(context -> {
            BudgetDTO source = context.getSource();
            Budget destination = context.getDestination();

            if (source.getMediaId() != null) {
                Media media = new Media();
                media.setId(source.getMediaId());
                destination.setMedia(media);
            }

            return destination;
        });
    }

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
                .artist(Artist.builder().id(dto.getArtistId()).build())
                .media(Media.builder().id(dto.getMediaId()).build())
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
                .mediaId(budget.getMedia() != null ? budget.getMedia().getId() : null)
                .build();
    }

    public BudgetDTO toDtoWithResponse(Budget budget) {
        BudgetDTO dto = toDto(budget);
        dto.setResponse(budget.getResponse());
        return dto;
    }

    public Budget toEntityWithResponse(BudgetDTO dto) {
        Budget budget = toEntity(dto);
        budget.setResponse(dto.getResponse());
        return budget;
    }

    public List<BudgetDTO> toDto(List<Budget> budgets) {
        return budgets.stream()
                .map(this::toDto)
                .toList();
    }

    public List<Budget> toEntity(List<BudgetDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }

}
