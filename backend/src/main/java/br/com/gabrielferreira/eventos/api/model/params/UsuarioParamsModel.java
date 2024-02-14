package br.com.gabrielferreira.eventos.api.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioParamsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -2563566075955472473L;

    @Schema(description = "Id do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome do usuário", example = "Gabriel Ferreira")
    @Size(min = 1, max = 255)
    private String nome;

    @Schema(description = "E-mail do usuário", example = "teste@email.com")
    @Email
    @Size(min = 1, max = 255)
    private String email;

    @Schema(description = "Data cadastro do usuário", example = "2024-12-20")
    private LocalDate dataCadastro;

    @Schema(description = "Data atualização do usuário", example = "2024-12-20")
    private LocalDate dataAtualizacao;
}
