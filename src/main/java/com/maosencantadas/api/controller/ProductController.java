package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ProductDTO;
import com.maosencantadas.api.mapper.ProductMapper;
import com.maosencantadas.model.domain.product.Product;
import com.maosencantadas.model.service.ProductService;
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
@RequiredArgsConstructor
@RequestMapping("/v1/products")
@CrossOrigin(origins = "*")
@Tag(name = "Products", description = "API for managing products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(summary = "List all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product list retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<Product> products = productService.findAll();
        List<ProductDTO> productDTOs = productMapper.toDTO(products);
        return ResponseEntity.ok(productDTOs);
    }

    @Operation(summary = "Get a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        Product product = productService.findById(id);
        ProductDTO productDTO = productMapper.toDTO(product);
        return ResponseEntity.ok(productDTO);
    }

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data provided",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product newProduct = productService.save(product);

        log.info("Product created with ID: {}", newProduct.getId());
        ProductDTO newProductDTO = productMapper.toDTO(newProduct);

        URI location = RestUtils.getUri(newProductDTO.getId());
        return ResponseEntity.created(location).body(newProductDTO);
    }

    @Operation(summary = "List products by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product list by category retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)))
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.findByCategory(categoryId);
        List<ProductDTO> productDTOs = productMapper.toDTO(products);
        log.info("Products found for category ID {}: {}", categoryId, products.size());
        return ResponseEntity.ok(productDTOs);
    }

    @Operation(summary = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        log.info("Updating product with ID: {}", id);
        Product product = productMapper.toEntity(productDTO);
        Product updatedProduct = productService.update(id, product);

        log.info("Product updated with ID: {}", updatedProduct.getId());
        ProductDTO updatedProductDTO = productMapper.toDTO(updatedProduct);
        return ResponseEntity.ok(updatedProductDTO);
    }

    @Operation(summary = "List products by artist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product list by artist retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)))
    })
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<ProductDTO>> getByArtist(@PathVariable Long artistId) {
        List<Product> products = productService.findByArtist(artistId);

        List<ProductDTO> productDTOs = productMapper.toDTO(products);
        log.info("Products found for artist ID {}: {}", artistId, products.size());

        return ResponseEntity.ok(productDTOs);
    }

    @Operation(summary = "Delete a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
