package com.maosencantadas.api.mapper;

import com.maosencantadas.api.dto.ProductAssessmentDTO;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.domain.product.ProductAssessment;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductAssessmentMapper {
    private final ModelMapper modelMapper;

    public ProductAssessmentMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Configuração de mapeamento manual
        TypeMap<ProductAssessment, ProductAssessmentDTO> typeMap = modelMapper.createTypeMap(ProductAssessment.class, ProductAssessmentDTO.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getCustomer().getId(), ProductAssessmentDTO::setCustomerId);
            mapper.map(src -> src.getProduct().getId(), ProductAssessmentDTO::setProductId);
        });
    }

    public ProductAssessmentDTO toDto(ProductAssessment productAssessment) {
        if (productAssessment == null) {
            return null;
        }

        ProductAssessmentDTO dto = modelMapper.map(productAssessment, ProductAssessmentDTO.class);

        // Conversão manual para evitar problemas com objetos nulos
        if (productAssessment.getCustomer() != null) {
            dto.setCustomerId(productAssessment.getCustomer().getId());
        }
        if (productAssessment.getProduct() != null) {
            dto.setProductId(productAssessment.getProduct().getId());
        }

        return dto;
    }

    public ProductAssessment toEntity(ProductAssessmentDTO productAssessmentDTO) {
        ProductAssessment productAssessment = modelMapper.map(productAssessmentDTO, ProductAssessment.class);

        // Conversão manual de customerId para Customer
        if (productAssessmentDTO.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setId(productAssessmentDTO.getCustomerId());
            productAssessment.setCustomer(customer);
        }

        // Conversão manual de productId para Product
        if (productAssessmentDTO.getProductId() != null) {
            Product product = new Product();
            product.setId(productAssessmentDTO.getProductId());
            productAssessment.setProduct(product);
        }

        return productAssessment;
    }

    public List<ProductAssessmentDTO> toDtoList(List<ProductAssessment> productAssessments) {
        return productAssessments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}