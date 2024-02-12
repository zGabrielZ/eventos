package br.com.gabrielferreira.eventos.api.model.params;

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

    private Long id;

    @Size(min = 1,max = 255)
    private String nome;

    private LocalDate data;

    @URL
    @Size(min = 1, max = 255)
    private String url;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;

    @Size(min = 1, max = 255)
    private String localidade;

    @Size(min = 1, max = 10)
    private String uf;
}
