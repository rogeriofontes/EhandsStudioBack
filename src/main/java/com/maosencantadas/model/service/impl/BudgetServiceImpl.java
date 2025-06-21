package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.api.mapper.BudgetMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.repository.BudgetRepository;
import com.maosencantadas.model.repository.CustomerRepository;
import com.maosencantadas.model.repository.ProductRepository;
import com.maosencantadas.model.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public List<BudgetDTO> findAllBudgets() {
        log.info("Listing all budgets");
        List<Budget> budgets = budgetRepository.findAll();
        return budgets.stream()
                .map(budgetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetDTO findBudgetById(Long id) {
        log.info("Finding budget by id: {}", id);
        Optional<Budget> budget = budgetRepository.findById(id);
        if (budget.isEmpty()) {
            log.warn("Budget not found with id: {}", id);
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        return budgetMapper.toDto(budget.get());
    }

    @Override
    public BudgetDTO createBudget(BudgetDTO dto) {
        log.info("Creating new budget");

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + dto.getCustomerId()));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + dto.getProductId()));

        Budget budget = budgetMapper.toEntity(dto);
        budget.setCustomer(customer);
        budget.setProduct(product);

        Budget saved = budgetRepository.save(budget);
        return budgetMapper.toDto(saved);
    }


    @Override
    public BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO) {
        log.info("Updating budget with id: {}", id);

        Customer customer = customerRepository.findById(budgetDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + budgetDTO.getCustomerId()));

        Product product = productRepository.findById(budgetDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + budgetDTO.getProductId()));

        Budget budgetUpdated = budgetRepository.findById(id)
                .map(budget -> {
                    budget.setStatus(budgetDTO.getStatus());
                    budget.setDateBudget(budgetDTO.getDateBudget());
                    budget.setDescription(budgetDTO.getDescription());
                    budget.setResponse(budgetDTO.getResponse());
                    budget.setImageUrl(budgetDTO.getImageUrl());
                    budget.setCustomer(customer);
                    budget.setProduct(product);
                    return budgetRepository.save(budget);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id " + id));
        return budgetMapper.toDto(budgetUpdated);
    }

    @Override
    public BudgetDTO createBudgetWithImage(String description, Long productId, Long customerId, MultipartFile image) {
        log.info("Creating budget with image (customerId={}, productId={})", customerId, productId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        Budget budget = new Budget();
        budget.setDescription(description);
        budget.setCustomer(customer);
        budget.setProduct(product);
        budget.setStatus("PENDING");

        if (image != null && !image.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(image.getOriginalFilename());
                Path uploadDir = Paths.get("uploads/budgets");
                Files.createDirectories(uploadDir);

                Path filePath = uploadDir.resolve(fileName);
                image.transferTo(filePath.toFile());

                String imageUrl = "/uploads/budgets/" + fileName;
                budget.setImageUrl(imageUrl);
            } catch (IOException e) {
                log.error("Error saving budget image", e);
                throw new RuntimeException("Error saving image", e);
            }
        }

        Budget saved = budgetRepository.save(budget);
        return budgetMapper.toDto(saved);
    }

    @Override
    public BudgetDTO findBudgetByCustomerId(Long customerID) {
        Optional<Budget> budgetByCustomerId = budgetRepository.findBudgetByCustomerId(customerID);
        if (budgetByCustomerId.isEmpty()) {
            log.warn("Budget not found for customer with id: {}", customerID);
            throw new ResourceNotFoundException("Budget not found for customer with id: " + customerID);
        }
        log.info("Budget found for customer with id: {}", customerID);
        Budget budget = budgetByCustomerId.get();
        return budgetMapper.toDto(budget);
    }

    @Override
    public void deleteBudget(Long id) {
        log.info("Deleting budget with id: {}", id);
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        budgetRepository.deleteById(id);
    }
}
