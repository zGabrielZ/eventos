package br.com.gabrielferreira.eventos.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Id da cidade", example = "1")
    private Long id;

    @Schema(description = "Data cadastro da cidade", example = "2024-02-11T16:49:23.177681-03:00")
    private ZonedDateTime dataCadastro;

    @Schema(description = "Data atualização da cidade", example = "2024-02-11T16:49:23.177681-03:00")
    private ZonedDateTime dataAtualizacao;
}
