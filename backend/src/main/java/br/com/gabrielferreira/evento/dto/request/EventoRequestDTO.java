package br.com.gabrielferreira.evento.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventoRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 958714108846456044L;

    @NotBlank(message = "Nome do evento não pode ser vazio")
    @Size(max = 150, message = "O nome do evento deve ter no máximo 150 caracteres")
    private String nome;

    @NotNull(message = "Data do evento não pode ser vazio")
    private LocalDate data;

    @Size(max = 250, message = "A url do evento deve ter no máximo 250 caracteres")
    private String url;

    @Valid
    @NotNull(message = "A cidade não pode ser vazia")
    private CidadeIdRequestDTO cidade;
}
