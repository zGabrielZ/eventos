package br.com.gabrielferreira.evento.exception;

import java.io.Serial;

public class NaoEncontradoException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3091570466772691949L;

    public NaoEncontradoException(String msg){
        super(msg);
    }
}
