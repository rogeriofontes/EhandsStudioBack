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
        if (dto == null) {
            return null;
        }

        Budget budget = modelMapper.map(dto, Budget.class);

        // Set Customer, Product, Artist if IDs are present
        if (dto.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.getPerson().setId(dto.getCustomerId());
            budget.setCustomer(customer);
        }
        if (dto.getProductId() != null) {
            Product product = new Product();
            product.setId(dto.getProductId());
            budget.setProduct(product);
        }
        if (dto.getArtistId() != null) {
            Artist artist = new Artist();
            artist.getPerson().setId(dto.getArtistId());
            budget.setArtist(artist);
        }

        return budget;
    }

    public BudgetDTO toDto(Budget budget) {
        if (budget == null) {
            return null;
        }

        BudgetDTO dto = modelMapper.map(budget, BudgetDTO.class);

        // Set IDs for Customer, Product, Artist, and Media
        if (budget.getCustomer() != null) {
            dto.setCustomerId(budget.getCustomer().getPerson().getId());
        }
        if (budget.getProduct() != null) {
            dto.setProductId(budget.getProduct().getId());
        }
        if (budget.getArtist() != null) {
            dto.setArtistId(budget.getArtist().getPerson().getId());
        }
        if (budget.getMedia() != null) {
            dto.setMediaId(budget.getMedia().getId());
        }

        return dto;
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
