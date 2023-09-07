package br.com.gabrielferreira.evento.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbstractModelConsulta implements Serializable {

    @Serial
    private static final long serialVersionUID = 2207588700867108682L;

    private String atributoDto;

    private String atributoEntidade;
}
