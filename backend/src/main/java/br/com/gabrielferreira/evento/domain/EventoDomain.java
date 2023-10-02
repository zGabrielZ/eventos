package br.com.gabrielferreira.evento.domain;

import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EventoDomain implements Serializable {

    @Serial
    private static final long serialVersionUID = -1094994962508191281L;

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private LocalDate dataEvento;

    private String url;

    private CidadeDomain cidade;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}
