package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.api.dto.BudgetResponseDTO;
import com.maosencantadas.api.mapper.BudgetMapper;
import com.maosencantadas.model.domain.budget.Budget;
import com.maosencantadas.model.service.BudgetService;
import com.maosencantadas.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/budgets")
@CrossOrigin(origins = "*")
@Tag(name = "Budget")//, description = "API for managing budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetMapper budgetMapper;

    @Operation(summary = "List all budgets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of budgets",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<BudgetDTO>> findAll() {
        List<Budget> budgets = budgetService.findAll();
        List<BudgetDTO> budgetDTOS = budgetMapper.toDto(budgets);
        return ResponseEntity.ok(budgetDTOS);
    }

    @Operation(summary = "Get a budget by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Budget found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetDTO.class))),
            @ApiResponse(responseCode = "404", description = "Budget not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> findById(@PathVariable Long id) {
        Budget budget = budgetService.findById(id);
        BudgetDTO budgetDTO = budgetMapper.toDto(budget);
        return ResponseEntity.ok(budgetDTO);
    }

    @Operation(summary = "Get a budget by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Budget found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetDTO.class))),
            @ApiResponse(responseCode = "404", description = "Budget not found", content = @Content)
    })
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<BudgetDTO> findByCustomerId(@PathVariable("customerId") Long customerId) {
        Budget budget = budgetService.findByCustomerId(customerId);
        BudgetDTO budgetDTO = budgetMapper.toDto(budget);
        return ResponseEntity.ok(budgetDTO);
    }

    @Operation(summary = "Create a new budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Budget successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BudgetDTO> create(@RequestBody BudgetDTO request) {
        Budget budget = budgetMapper.toEntity(request);
        Budget created = budgetService.create(budget);

        BudgetDTO createdDTO = budgetMapper.toDto(created);
        URI location = RestUtils.getUri(createdDTO.getId());
        return ResponseEntity.created(location).body(createdDTO);
    }

    @Operation(summary = "Create a new budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Budget successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content)
    })
    @PatchMapping(value = "/{budgetId}/response", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BudgetDTO> createResponse(
            @PathVariable("budgetId") Long budgetId,
            @RequestBody BudgetResponseDTO budgetResponseDTO) {
        Budget created = budgetService.createResponse(budgetId, budgetResponseDTO);
        BudgetDTO createdDTO = budgetMapper.toDtoWithResponse(created);
        URI location = RestUtils.getUri(createdDTO.getId());
        return ResponseEntity.created(location).body(createdDTO);
    }

    @Operation(summary = "Update an existing budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Budget successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetDTO.class))),
            @ApiResponse(responseCode = "404", description = "Budget not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> update(@PathVariable Long id, @Valid @RequestBody BudgetDTO budgetDTO) {
        log.info("Updating budget with ID: {}", id);
        Budget updatedBudget = budgetMapper.toEntity(budgetDTO);
        Budget budget = budgetService.update(id, updatedBudget);

        log.info("Budget updated successfully: {}", budget.getId());
        budgetDTO = budgetMapper.toDto(budget);
        return ResponseEntity.ok(budgetDTO);
    }

    @Operation(summary = "Delete a budget by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Budget successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Budget not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        budgetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
