package com.maosencantadas.domain.orcamento;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maosencantadas.domain.cliente.Cliente;
import com.maosencantadas.domain.produto.Produto;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Table(name = "Orcamentos")
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataOrcamento;

    private String imagemUrl;  // Novo campo

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id") 
    private Cliente cliente;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "produtos_id", referencedColumnName = "id") 
    private Produto produto;

    
}
