package br.com.gabrielferreira.eventos.api.model;

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
public class EventoResumidoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -807270475816309016L;

    private Long id;

    private String nome;

    private LocalDate data;

    private String url;

    private CidadeModel cidade;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;
}
