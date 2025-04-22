package com.maosencantadas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String nome;
    private String endereco;
    private String email;
    private String telefone;
   
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }   

    public String getEndereco() {
        return endereco;
    }   

    public void setEndereco(String endereco) {
        if (endereco != null && !endereco.trim().isEmpty()) {
            this.endereco = "https://www.google.com/maps/search/?api=1&query=" + endereco.replace(" ", "+");
        } else {
            this.endereco = null;
        }
    }   

    public String getEmail() {
        return email;
    }   

    public void setEmail(String email) {
        this.email = email;
    }   

    public String getTelefone() {
        return telefone;
    }   

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }   
}
