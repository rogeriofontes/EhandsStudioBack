package com.maosencantadas.model.domain.artista;

import com.maosencantadas.model.domain.categoria.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Table(name = "artistas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Schema(name = "Artista", description = "representa um artista")

public class Artista {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identifica o artista", example = "1")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome do artista", example = "Amanda Testando")
    private String nome;

    @Schema(description = "URL da foto do artista", example = "https://example.com/foto.jpg")
    private String foto;

    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    @Schema(description = "Endereço do artista", example = "Rua Padre Pio, 123 - Centro")
    private String endereco;

    @Email(message = "Email inválido")
    @Schema(description = "Endereço de email do artista", example = "teste@exemplo.com")
    private String email;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Schema(description = "Número de telefone do artista", example = "(34) 98765-4321")
    private String telefone;

    @Schema(description = "Perfil do Instagram do artista", example = "@testandoartista")
    private String insta;

    @Schema(description = "Perfil do Facebook do artista", example = "facebook.com/testandoartista")
    private String face;

    @Schema(description = "Número do WhatsApp do artista", example = "(11) 91234-5678")
    private String whatsapp;

    @NotBlank(message = "CPF é obrigatório")
    @Schema(description = "CPF do artista", example = "123.456.789-00")
    private String cpf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}

