package com.maosencantadas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
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

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataOrcamento() {
        return dataOrcamento;
    }

    public void setDataOrcamento(LocalDateTime dataOrcamento) {
        this.dataOrcamento = dataOrcamento;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
