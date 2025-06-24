package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.CustomerDTO;
import com.maosencantadas.api.mapper.CustomerMapper;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.service.CustomerService;
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
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Customer", description = "Operations related to system clients")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    @Operation(summary = "List all customers", description = "Returns a list with all registered customers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers successfully listed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CustomerDTO>> findAll() {
        log.info("Listing all customers");
        List<Customer> customers = customerService.findAll();
        List<CustomerDTO> customerDTOS = customerMapper.toDTO(customers);
        return ResponseEntity.ok(customerDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find customer by ID", description = "Returns a specific customer by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerDTO> findById(@PathVariable("id") Long id) {
        log.info("Finding customer by ID: {}", id);
        Customer customer = customerService.findById(id);
        CustomerDTO customerDTO = customerMapper.toDTO(customer);
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
    public ResponseEntity<CustomerDTO> create(
            @Schema(description = "New customer data", requiredMode = Schema.RequiredMode.REQUIRED)
            @RequestBody CustomerDTO customerDTO) {
        log.info("Creating new customer: {}", customerDTO.getName());
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer newCustomer = customerService.save(customer);

        log.info("New customer created with ID: {}", newCustomer.getId());
        CustomerDTO customerDTOResponse = customerMapper.toDTO(newCustomer);
        URI location = RestUtils.getUri(customerDTOResponse.getId());
        return ResponseEntity.created(location).body(customerDTOResponse);
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
    public ResponseEntity<CustomerDTO> update(
            @Schema(description = "ID of the customer to be updated", example = "1") @PathVariable("id") Long id,
            @Schema(description = "Updated customer data", requiredMode = Schema.RequiredMode.REQUIRED)
            @RequestBody CustomerDTO customerDTO) {
        log.info("Updating customer with id: {}", id);
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer updatedCustomer = customerService.update(id, customer);
        CustomerDTO customerDTOResponse = customerMapper.toDTO(updatedCustomer);
        return ResponseEntity.ok(customerDTOResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client", description = "Deletes an existing customer by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(
            @Schema(description = "ID of the customer to be deleted", example = "1")
            @PathVariable("id") Long id) {
        log.info("Deleting customer by ID: {}", id);
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
