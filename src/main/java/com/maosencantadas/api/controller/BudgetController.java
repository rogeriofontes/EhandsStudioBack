package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.BudgetDTO;
import com.maosencantadas.model.service.BudgetService;
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
@Tag(name = "Budget", description = "API for managing budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @Operation(summary = "List all budgets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of budgets",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<BudgetDTO>> findAllBudgets() {
        List<BudgetDTO> budgets = budgetService.findAllBudgets();
        return ResponseEntity.ok(budgets);
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
        BudgetDTO budget = budgetService.findBudgetById(id);
        return ResponseEntity.ok(budget);
    }

    @Operation(summary = "Create a new budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Budget successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BudgetDTO> create(
            @RequestParam("description") String description,
            @RequestParam("productId") Long productId,
            @RequestParam("customerId") Long customerId,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        BudgetDTO created = budgetService.createBudgetWithImage(description, productId, customerId, image);
        return ResponseEntity.created(URI.create("/v1/budgets/" + created.getId())).body(created);
    }


    @Operation(summary = "Update an existing budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Budget successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BudgetDTO.class))),
            @ApiResponse(responseCode = "404", description = "Budget not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> update(@PathVariable Long id, @Valid @RequestBody BudgetDTO updatedBudget) {
        BudgetDTO budget = budgetService.updateBudget(id, updatedBudget);
        return ResponseEntity.ok(budget);
    }

    @Operation(summary = "Delete a budget by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Budget successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Budget not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
