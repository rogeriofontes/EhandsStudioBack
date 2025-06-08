package com.maosencantadas.model.domain.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Schema(name = "Category", description = "Represents a category of products")
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifies the category", example = "1")
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Column(name = "name")
    @Schema(description = "Category name", example = "TestingCategory")
    private String name;

}
