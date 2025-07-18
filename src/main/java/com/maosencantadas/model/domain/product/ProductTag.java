package com.maosencantadas.model.domain.product;

import com.maosencantadas.model.domain.AuditDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_product_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTag extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;
}