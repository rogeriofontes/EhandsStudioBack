package com.maosencantadas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String nome;
    private String foto;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
