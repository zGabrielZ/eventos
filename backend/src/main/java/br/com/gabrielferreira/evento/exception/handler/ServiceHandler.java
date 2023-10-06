package br.com.gabrielferreira.evento.exception.handler;

import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.exception.model.ErroPadrao;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.evento.utils.DataUtils.*;

@ControllerAdvice
public class ServiceHandler {

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> naoEncontratoException(NaoEncontradoException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErroPadrao erroPadrao = gerarErroPadrao(httpStatus, ZonedDateTime.now(FUSO_HORARIO_PADRAO_SISTEMA), "Não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadrao);
    }

    @ExceptionHandler(MsgException.class)
    public ResponseEntity<ErroPadrao> msgException(MsgException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErroPadrao erroPadrao = gerarErroPadrao(httpStatus, ZonedDateTime.now(FUSO_HORARIO_PADRAO_SISTEMA), "Erro personalizado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadrao);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> exception(Exception e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErroPadrao erroPadrao = gerarErroPadrao(httpStatus, ZonedDateTime.now(FUSO_HORARIO_PADRAO_SISTEMA), e.getMessage(), "Erro, tente mais tarde", request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadrao);
    }

    private ErroPadrao gerarErroPadrao(HttpStatus httpStatus, ZonedDateTime momento, String erro, String mensagem, String caminhoUrl){
        return ErroPadrao.builder()
                .momento(momento)
                .status(httpStatus.value())
                .erro(erro)
                .mensagem(mensagem)
                .caminhoUrl(caminhoUrl)
                .build();
    }
}
