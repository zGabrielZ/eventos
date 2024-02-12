package br.com.gabrielferreira.eventos.domain.dao.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventoProjection implements Serializable {

    @Serial
    private static final long serialVersionUID = 595239183578280342L;

    private Long id;

    private String nome;

    private LocalDate data;

    private String url;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;

    private Long idCidade;

    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private ZonedDateTime dataCadastroCidade;

    private ZonedDateTime dataAtualizacaoCidade;
}
