package br.com.gabrielferreira.evento.exception;

import java.io.Serial;

public class MsgException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3091570466772691949L;

    public MsgException(String msg){
        super(msg);
    }
}
