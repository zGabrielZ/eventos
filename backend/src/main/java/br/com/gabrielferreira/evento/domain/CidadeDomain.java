package br.com.gabrielferreira.evento.domain;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CidadeDomain implements Serializable {

    @Serial
    private static final long serialVersionUID = -1094994962508191281L;

    private Long id;

    private String nome;

    private String codigo;
}
