package br.com.gabrielferreira.eventos.api.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventoParamsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -507023954304201242L;

    @Schema(description = "Id do evento", example = "1")
    private Long id;

    @Schema(description = "Nome do evento", example = "Evento #1")
    @Size(min = 1,max = 255)
    private String nome;

    @Schema(description = "Data do evento", example = "2024-12-22")
    private LocalDate data;

    @Schema(description = "Url do evento", example = "https://www.youtube.com")
    @URL
    @Size(min = 1, max = 255)
    private String url;

    @Schema(description = "Data cadastro do evento", example = "2024-12-22")
    private LocalDate dataCadastro;

    @Schema(description = "Data atualização do evento", example = "2024-12-22")
    private LocalDate dataAtualizacao;

    @Schema(description = "Localidade do evento", example = "São Paulo")
    @Size(min = 1, max = 255)
    private String localidade;

    @Schema(description = "Uf do evento", example = "SP")
    @Size(min = 1, max = 10)
    private String uf;
}
