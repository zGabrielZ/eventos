package br.com.gabrielferreira.eventos.api.model.input;

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

    @NotBlank
    @Size(min = 1, max = 255)
    private String nome;

    @Email
    @NotBlank
    @Size(min = 1, max = 255)
    private String email;

    @NotBlank
    @Size(min = 1, max = 255)
    private String senha;

    @Valid
    @NotNull
    @NotEmpty
    private List<PerfilIdInputModel> perfis = new ArrayList<>();
}
