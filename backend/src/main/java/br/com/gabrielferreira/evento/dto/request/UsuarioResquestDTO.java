package br.com.gabrielferreira.evento.dto.request;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResquestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8392496183140825629L;

    @NotBlank(message = "Nome do usuário não pode ser vazio")
    @Size(max = 150, message = "O nome do usuário deve ter no máximo 150 caracteres")
    private String nome;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail do usuário não pode ser vazio")
    @Size(max = 150, message = "O e-mail do usuário deve ter no máximo 150 caracteres")
    private String email;

    @NotBlank(message = "Senha do usuário não pode ser vazio")
    @Size(max = 150, message = "A senha do usuário deve ter no máximo 150 caracteres")
    private String senha;

    @Valid
    @NotNull(message = "O perfil não pode ser nulo")
    @NotEmpty(message = "O perfil não pode ser vazio")
    private List<PerfilIdResquestDTO> perfis = new ArrayList<>();
}
