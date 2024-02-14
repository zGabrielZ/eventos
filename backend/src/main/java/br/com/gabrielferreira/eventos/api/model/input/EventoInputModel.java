package br.com.gabrielferreira.eventos.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventoInputModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -4454428904630791421L;

    @Schema(description = "Nome do evento", example = "Evento #1")
    @NotBlank
    @Size(min = 1,max = 255)
    private String nome;

    @Schema(description = "Data do evento", example = "2024-12-20")
    @FutureOrPresent
    @NotNull
    private LocalDate data;

    @Schema(description = "Url do evento", example = "https://www.youtube.com")
    @URL
    @Size(min = 1, max = 255)
    private String url;

    @Schema(description = "Cidade do evento")
    @Valid
    @NotNull
    private CidadeInputModel cidade;
}
