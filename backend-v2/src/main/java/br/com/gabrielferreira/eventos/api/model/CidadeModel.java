package br.com.gabrielferreira.eventos.api.model;

import lombok.*;

import java.io.Serial;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CidadeModel extends CepModel {

    @Serial
    private static final long serialVersionUID = 2651757834786988277L;

    private Long id;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;
}
