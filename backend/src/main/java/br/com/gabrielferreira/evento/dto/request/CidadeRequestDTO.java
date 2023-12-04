package br.com.gabrielferreira.evento.dto.request;

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
public class CidadeRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 958714108846456044L;

    @NotBlank(message = "Nome da cidade não pode ser vazio")
    @Size(max = 255, message = "O nome da cidade deve ter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "Código da cidade não pode ser vazio")
    @Size(max = 150, message = "O código da cidade deve ter no máximo 150 caracteres")
    private String codigo;
}
