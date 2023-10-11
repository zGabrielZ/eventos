package br.com.gabrielferreira.evento.exception.model;

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
public class ErroFormulario implements Serializable {

    @Serial
    private static final long serialVersionUID = 5300537359988028427L;

    private String campo;
    private String mensagem;
}

