package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.QuoteMessageDTO;
import com.maosencantadas.api.mapper.QuoteMessageMapper;
import com.maosencantadas.model.domain.budget.QuoteMessage;
import com.maosencantadas.model.service.QuoteMessageService;
import com.maosencantadas.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/quote-messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Category", description = "Operations related to product categories")
public class QuoteMessageController {

    private final QuoteMessageService quoteMessageService;
    private final QuoteMessageMapper quoteMessageMapper;

    @GetMapping
    @Operation(summary = "List all categories", description = "Returns a list of all available categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories successfully listed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuoteMessageDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<QuoteMessageDTO>> findAll() {
        log.info("Listing all categories");
        List<QuoteMessage> categories = quoteMessageService.findAll();
        List<QuoteMessageDTO> quoteMessageDTOS = quoteMessageMapper.toDTOList(categories);
        return ResponseEntity.ok(quoteMessageDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find category by ID", description = "Returns a specific category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuoteMessageDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<QuoteMessageDTO> findById(@PathVariable("id") Long id) {
        log.info("Finding category by ID: {}", id);
        QuoteMessage quoteMessage = quoteMessageService.findById(id);
        QuoteMessageDTO quoteMessageDTO = quoteMessageMapper.toDTO(quoteMessage);
        log.info("Category found: {}", quoteMessageDTO.getId());
        return ResponseEntity.ok(quoteMessageDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates and returns a new category with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuoteMessageDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<QuoteMessageDTO> create(
            @RequestBody
            @Schema(description = "New category data", requiredMode = Schema.RequiredMode.REQUIRED)
            QuoteMessageDTO quoteMessageDTO) {
        log.info("Creating new category: {}", quoteMessageDTO.getId());
        QuoteMessage quoteMessage = quoteMessageMapper.toEntity(quoteMessageDTO);
        QuoteMessage newQuoteMessage = quoteMessageService.save(quoteMessage);

        log.info("Category created with ID: {}", newQuoteMessage.getId());
        QuoteMessageDTO newQuoteMessageDTO = quoteMessageMapper.toDTO(newQuoteMessage);
        URI location = RestUtils.getUri(newQuoteMessageDTO.getId());
        return ResponseEntity.created(location).body(newQuoteMessageDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category", description = "Update an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuoteMessageDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<QuoteMessageDTO> update(
            @PathVariable("id")
            @Schema(description = "ID of the category to be updated", example = "1")
            Long id,
            @RequestBody
            @Schema(description = "Updated category data", requiredMode = Schema.RequiredMode.REQUIRED)
            QuoteMessageDTO quoteMessageDTO) {
        log.info("Updating category with id: {}", id);
        QuoteMessage quoteMessage = quoteMessageMapper.toEntity(quoteMessageDTO);
        QuoteMessage updatedQuoteMessage = quoteMessageService.update(id, quoteMessage);

        log.info("Category updated: {}", updatedQuoteMessage.getId());
        QuoteMessageDTO updatedQuoteMessageDTO = quoteMessageMapper.toDTO(updatedQuoteMessage);
        return ResponseEntity.ok(updatedQuoteMessageDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by ID", description = "Deletes an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(
            @PathVariable("id")
            @Schema(description = "ID of the category to be deleted", example = "1")
            Long id) {
        log.info("Deleting category by ID: {}", id);
        quoteMessageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
