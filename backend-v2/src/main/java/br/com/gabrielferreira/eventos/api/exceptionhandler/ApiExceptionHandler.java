package br.com.gabrielferreira.eventos.api.exceptionhandler;

import br.com.gabrielferreira.eventos.api.mapper.ErroPadraoMapper;
import br.com.gabrielferreira.eventos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.eventos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.eventos.domain.exception.UnauthorizedException;
import br.com.gabrielferreira.eventos.domain.exception.model.ErroPadraoCamposModel;
import br.com.gabrielferreira.eventos.domain.exception.model.ErroPadraoFormularioModel;
import br.com.gabrielferreira.eventos.domain.exception.model.ErroPadraoModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZonedDateTime;
import java.util.List;

import static br.com.gabrielferreira.eventos.common.utils.DataUtils.*;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {

    private final ErroPadraoMapper erroPadraoMapper;

    private final MessageSource messageSource;

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErroPadraoModel> naoEncontradoException(NaoEncontradoException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErroPadraoModel erroPadraoModel = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadraoModel);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroPadraoModel> regraDeNegocioException(RegraDeNegocioException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErroPadraoModel erroPadraoModel = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Regra de negócio", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadraoModel);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadraoModel> validacaoException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErroPadraoCamposModel erroPadraoCamposModel = erroPadraoMapper.toErroPadraoCampos(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Erro validação de campos", "Ocorreu um erro de validação nos campos", request.getRequestURI());

        List<ErroPadraoFormularioModel> campos = e.getBindingResult().getFieldErrors().stream()
                .map(campo -> erroPadraoMapper.toErroPadraoFormulario(campo.getField(), messageSource.getMessage(campo, LocaleContextHolder.getLocale())))
                .toList();
        erroPadraoCamposModel.setCampos(campos);

        return ResponseEntity.status(httpStatus).body(erroPadraoCamposModel);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadraoModel> erroException(Exception e, HttpServletRequest request){
        log.error(e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErroPadraoModel erroPadraoModel = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Erro inesperado", "Ocorreu um erro inesperado no sistema, tente mais tarde", request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadraoModel);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErroPadraoModel> unauthorizedException(UnauthorizedException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErroPadraoModel erroPadraoModel = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Não autorizado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadraoModel);
    }
}
