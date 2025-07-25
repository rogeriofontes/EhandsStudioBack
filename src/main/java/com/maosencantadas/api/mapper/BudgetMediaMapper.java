package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.BudgetMediaDTO;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.budget.BudgetMedia;
import com.maosencantadas.model.domain.media.Media;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class BudgetMediaMapper {

    private final ModelMapper modelMapper;

    public BudgetMediaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Entity -> DTO
        TypeMap<BudgetMedia, BudgetMediaDTO> toDTO = modelMapper.createTypeMap(BudgetMedia.class, BudgetMediaDTO.class);
        toDTO.setPostConverter(context -> {
            BudgetMedia source = context.getSource();
            BudgetMediaDTO dest = context.getDestination();
            dest.setBudgetId(source.getBudget() != null ? source.getBudget().getId() : null);
            dest.setMediaId(source.getMedia() != null ? source.getMedia().getId() : null);
            return dest;
        });

        // DTO -> Entity
        TypeMap<BudgetMediaDTO, BudgetMedia> toEntity = modelMapper.createTypeMap(BudgetMediaDTO.class, BudgetMedia.class);
        toEntity.setPostConverter(context -> {
            BudgetMediaDTO source = context.getSource();
            BudgetMedia dest = context.getDestination();

            if (source.getBudgetId() != null) {
                Budget budget = new Budget();
                budget.setId(source.getBudgetId());
                dest.setBudget(budget);
            }

            if (source.getMediaId() != null) {
                Media media = new Media();
                media.setId(source.getMediaId());
                dest.setMedia(media);
            }

            return dest;
        });
    }

    public BudgetMediaDTO toDTO(BudgetMedia entity) {
        return modelMapper.map(entity, BudgetMediaDTO.class);
    }

    public BudgetMedia toEntity(BudgetMediaDTO dto) {
        return modelMapper.map(dto, BudgetMedia.class);
    }
}
