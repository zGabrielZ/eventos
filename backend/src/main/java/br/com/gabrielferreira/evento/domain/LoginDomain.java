package br.com.gabrielferreira.evento.domain;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoginDomain implements Serializable {

    @Serial
    private static final long serialVersionUID = 4688454225000419939L;

    @EqualsAndHashCode.Include
    private Long idUsuario;

    private String tipo;

    private String token;

    private ZonedDateTime dataInicio;

    private ZonedDateTime dataFim;
}
