package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.CategoryDTO;
import com.maosencantadas.api.dto.CustomerDTO;
import com.maosencantadas.model.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Customer", description = "Operations related to system clients")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @Operation(summary = "List all customers", description = "Returns a list with all registered customers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers successfully listed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CustomerDTO>> findAllCustomers() {
        log.info("Listing all customers");
        List<CustomerDTO> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find customer by ID", description = "Returns a specific customer by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                   content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable("id")  Long id) {
        log.info("Finding customer by ID: {}", id);
        CustomerDTO customerDTO = customerService.findCustomerById(id);
        return customerDTO != null
                ? ResponseEntity.ok(customerDTO)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Creates and returns a new customer with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully",
                   content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerDTO> createCustomer(
            @RequestBody
            @Schema(description = "New customer data", required = true)
            CustomerDTO customerDTO) {
        log.info("Creating new customer: {}", customerDTO.getName());
        CustomerDTO newCustomer = customerService.saveCustomer(customerDTO);
        URI location = URI.create(String.format("/v1/customers/%s", newCustomer.getId()));
        return ResponseEntity.created(location).body(newCustomer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing customer", description = "Update an existing customer by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable("id")
            @Schema(description = "ID of the customer to be updated", example = "1")
            Long id,
            @RequestBody
            @Schema(description = "Updated customer data", required = true)
            CustomerDTO customerDTO) {
        log.info("Updating customer with id: {}", id);
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client", description = "Deletes an existing customer by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable("id")
            @Schema(description = "ID of the customer to be deleted", example = "1")
            Long id) {
        log.info("Deleting customer by ID: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
