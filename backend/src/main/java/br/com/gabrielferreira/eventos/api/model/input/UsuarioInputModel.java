package br.com.gabrielferreira.eventos.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioInputModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 9112651377514997761L;

    @Schema(description = "Nome do usuário", example = "Gabriel Ferreira")
    @NotBlank
    @Size(min = 1, max = 255)
    private String nome;

    @Schema(description = "E-mail do usuário", example = "teste@email.com")
    @Email
    @NotBlank
    @Size(min = 1, max = 255)
    private String email;

    @Schema(description = "Senha do usuário", example = "123")
    @NotBlank
    @Size(min = 1, max = 255)
    private String senha;

    @Schema(description = "Perfis do usuário")
    @Valid
    @NotNull
    @NotEmpty
    private List<PerfilIdInputModel> perfis = new ArrayList<>();
}
