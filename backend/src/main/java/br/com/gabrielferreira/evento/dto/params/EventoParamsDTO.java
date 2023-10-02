package br.com.gabrielferreira.evento.dto.params;

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
public class EventoParamsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3550554456478798801L;

    private Long id;

    private String nome;

    private LocalDate data;

    private String url;

    private Long idCidade;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
