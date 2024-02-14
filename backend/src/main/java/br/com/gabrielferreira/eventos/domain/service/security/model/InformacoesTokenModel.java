package br.com.gabrielferreira.eventos.domain.service.security.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Id do usuário", example = "1")
    private Long idUsuario;

    @Schema(description = "Tipo do token", example = "Bearer")
    private String tipo;

    @Schema(description = "Token", example = "WQQWOFQJWFPQJEFQFQ")
    private String token;

    @Schema(description = "Data início do login", example = "2024-02-11T16:49:23.177681-03:00")
    private ZonedDateTime dataInicio;

    @Schema(description = "Data fim do login", example = "2024-02-11T17:49:23.177681-03:00")
    private ZonedDateTime dataFim;
}
