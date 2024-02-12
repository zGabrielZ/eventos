package br.com.gabrielferreira.eventos.domain.exception;

import java.io.Serial;

public class RegraDeNegocioException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RegraDeNegocioException(String msg){
        super(msg);
    }
}
