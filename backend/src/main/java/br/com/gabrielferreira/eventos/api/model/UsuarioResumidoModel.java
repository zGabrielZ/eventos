package br.com.gabrielferreira.eventos.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResumidoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 150854081687212908L;

    private Long id;

    private String nome;

    private String email;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;
}
