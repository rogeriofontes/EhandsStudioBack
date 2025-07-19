package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.QuoteMessageDTO;
import com.maosencantadas.model.domain.budget.QuoteMessage;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuoteMessageMapper {

    public QuoteMessage toEntity(QuoteMessageDTO dto) {
        if (dto == null) {
            return null;
        }

        return QuoteMessage.builder()
                .id(dto.getId())
                .budget(dto.getBudget())
                .message(dto.getMessage())
                .sentByCustomer(dto.getSentByCustomer())
                .sentAt(dto.getSentAt())
                .build();
    }

    public QuoteMessageDTO toDTO(QuoteMessage entity) {
        if (entity == null) {
            return null;
        }

        return QuoteMessageDTO.builder()
                .id(entity.getId())
                .budget(entity.getBudget())
                .message(entity.getMessage())
                .sentByCustomer(entity.getSentByCustomer())
                .sentAt(entity.getSentAt())
                .build();
    }

    public List<QuoteMessageDTO> toDTOList(List<QuoteMessage> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}