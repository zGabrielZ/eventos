package br.com.gabrielferreira.evento.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5480279668640354724L;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail não pode ser vazio")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
    private String email;

    @NotBlank(message = "Senha não pode ser vazio")
    @Size(max = 150, message = "A senha deve ter no máximo 150 caracteres")
    private String senha;
}
