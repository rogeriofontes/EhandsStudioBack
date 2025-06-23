package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.api.dto.BudgetResponseDTO;
import com.maosencantadas.api.mapper.BudgetMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.media.Media;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.repository.*;
import com.maosencantadas.model.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Objects;
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
    private final ArtistRepository artistRepository;
    private final MediaRepository mediaRepository;

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

        Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + dto.getArtistId()));

        Media media = mediaRepository.findById(dto.getMediaId()).orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + dto.getMediaId()));

        Budget budget = budgetMapper.toEntity(dto);

        budget.setCustomer(customer);
        budget.setProduct(product);
        budget.setArtist(artist);
        budget.setMedia(media);

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

        Artist artist = artistRepository.findById(budgetDTO.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + budgetDTO.getArtistId()));

        Media media = mediaRepository.findById(budgetDTO.getMediaId()).orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + budgetDTO.getMediaId()));

        Budget budgetUpdated = budgetRepository.findById(id)
                .map(budget -> {
                    budget.setBudgetStatus(budgetDTO.getStatus());
                    budget.setDateBudget(budgetDTO.getDateBudget());
                    budget.setDescription(budgetDTO.getDescription());
                    budget.setResponse(budgetDTO.getResponse());
                    budget.setImageUrl(budgetDTO.getImageUrl());
                    budget.setCustomer(customer);
                    budget.setProduct(product);
                    budget.setArtist(artist);
                    budget.setMedia(media);
                    return budgetRepository.save(budget);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id " + id));
        return budgetMapper.toDto(budgetUpdated);
    }

    @Override
    public BudgetDTO createBudgetWithoutImage(BudgetDTO request) {
        log.info("Creating budget with image (customerId={}, productId={})", request.getCustomerId(), request.getProductId());

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + request.getArtistId()));

        Media media = mediaRepository.findById(request.getMediaId()).orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + request.getMediaId()));

        Budget budget = Budget.builder()
                .description(request.getDescription())
                .customer(customer)
                .product(product)
                .artist(artist)
                .budgetStatus("PENDING")
                .dateBudget(request.getDateBudget())
                .imageUrl(request.getImageUrl())
                .media(media)
                .build();

        Budget saved = budgetRepository.save(budget);
        log.info("Budget created with id: {}", saved.getId());
        return budgetMapper.toDto(saved);
    }

    @Override
    public BudgetDTO createBudgetResponse(Long budgetId, BudgetResponseDTO budgetResponseDTO) {
        Optional<Budget> budget = budgetRepository.findById(budgetId);
        if (budget.isEmpty()) {
            log.warn("Budget for record resonse, not found with id: {}", budgetId);
            throw new ResourceNotFoundException("Budget not found with id: " + budgetId);
        }

        Budget existingBudget = budget.get();
        existingBudget.setResponse(budgetResponseDTO.getResponse());
        Budget updatedBudget = budgetRepository.save(existingBudget);

        log.info("Budget response updated for budget with id: {}", budgetId);
        return budgetMapper.toDto(updatedBudget);
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
