package com.maosencantadas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String email;
    private String telefone;
    private String insta;
    private String face;

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
        this.endereco = endereco;
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

    public String getInsta() {
        return insta;
    }       

    public void setInsta(String insta) {
        this.insta = insta;
    }       
    
    public String getFace() {
        return face;
    }       

    public void setFace(String face) {
        this.face = face;
    }           

}
