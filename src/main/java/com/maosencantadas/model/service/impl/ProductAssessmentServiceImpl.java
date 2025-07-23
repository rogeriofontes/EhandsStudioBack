package com.maosencantadas.model.service.impl;

import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.product.ProductAssessment;
import com.maosencantadas.model.repository.CustomerRepository;
import com.maosencantadas.model.repository.ProductAssessmentRepository;
import com.maosencantadas.model.service.ProductAssessmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductAssessmentServiceImpl implements ProductAssessmentService {

    private final ProductAssessmentRepository productAssessmentRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<ProductAssessment> findAll() {
        log.info("Listing all customers");
        return productAssessmentRepository.findAll();
    }

    @Override
    public ProductAssessment findById(Long id) {
        log.info("Finding customer by id: {}", id);
        return productAssessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductAssessment not found with id " + id));
    }

    @Override
    public ProductAssessment save(ProductAssessment productAssessment) {
        log.info("Saving new customer: {}", productAssessment.getName());

        if (productAssessment.getCustomer() == null) {
            throw new IllegalArgumentException("Customer must have a user with a valid ID");
        }

        Long customerId = productAssessment.getCustomer().getId();
        Optional<Customer> customerById = customerRepository.findById(customerId);
        if (customerById.isPresent()) {
            log.debug("User with ID {} already exists, using existing user", customerId);

            productAssessment.setCustomer(customerById.get());
            return productAssessmentRepository.save(productAssessment);
        }

        return null;
    }

    @Override
    public ProductAssessment update(Long id, ProductAssessment productAssessment) {
        log.info("Updating product with id: {}", id);
        log.info("Updating product assessment with id: {}", id);
        return productAssessmentRepository.findById(id)
                .map(existingProductAssessment -> {
                    existingProductAssessment.setName(productAssessment.getName());
                    existingProductAssessment.setRating(productAssessment.getRating());
                    existingProductAssessment.setCustomer(productAssessment.getCustomer());
                    existingProductAssessment.setProduct(productAssessment.getProduct());
                    existingProductAssessment.setMessage(productAssessment.getMessage());
                    existingProductAssessment.setSentAt(productAssessment.getSentAt());
                    return productAssessmentRepository.save(existingProductAssessment);
                })
                .orElseThrow(() -> new ResourceNotFoundException("ProductAssessment not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting customer with id: {}", id);
        if (!productAssessmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id " + id);
        }
        productAssessmentRepository.deleteById(id);
    }

    @Override
    public Optional<ProductAssessment> findByName(String name) {
        return productAssessmentRepository.findByName(name);
    }

    @Override
    public boolean existsById(Long id) {
        return productAssessmentRepository.existsById(id);
    }
}
