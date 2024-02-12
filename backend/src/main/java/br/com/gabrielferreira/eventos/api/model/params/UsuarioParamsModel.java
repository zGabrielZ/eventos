package br.com.gabrielferreira.eventos.api.model.params;

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

    private Long id;

    @Size(min = 1, max = 255)
    private String nome;

    @Email
    @Size(min = 1, max = 255)
    private String email;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;
}
