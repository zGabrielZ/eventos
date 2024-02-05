package br.com.gabrielferreira.eventos.domain.dao.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioProjection implements Serializable {

    @Serial
    private static final long serialVersionUID = -7161095512519691919L;

    private Long id;

    private String nome;

    private String email;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;
}
