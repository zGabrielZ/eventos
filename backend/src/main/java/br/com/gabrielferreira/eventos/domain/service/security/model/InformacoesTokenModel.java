package br.com.gabrielferreira.eventos.domain.service.security.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesTokenModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -417426178543900467L;

    private Long idUsuario;

    private String tipo;

    private String token;

    private ZonedDateTime dataInicio;

    private ZonedDateTime dataFim;
}
