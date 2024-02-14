package br.com.gabrielferreira.eventos.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -2497333484577159552L;

    @Schema(description = "Id do evento", example = "1")
    private Long id;

    @Schema(description = "Nome do evento", example = "Evento #1")
    private String nome;

    @Schema(description = "Data do evento", example = "2024-12-20")
    private LocalDate data;

    @Schema(description = "Url do evento", example = "https://www.youtube.com")
    private String url;

    @Schema(description = "Cidade do evento")
    private CidadeModel cidade;

    @Schema(description = "Usuário do evento")
    private UsuarioResumidoModel usuario;

    @Schema(description = "Data cadastro do evento", example = "2024-02-11T16:49:23.177681-03:00")
    private ZonedDateTime dataCadastro;

    @Schema(description = "Data atualização do evento", example = "2024-02-11T16:49:23.177681-03:00")
    private ZonedDateTime dataAtualizacao;
}
