package br.com.gabrielferreira.eventos.domain.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErroPadraoFormularioModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String campo;

    private String mensagem;
}

