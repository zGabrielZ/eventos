package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.domain.exception.model.ErroPadraoCamposModel;
import br.com.gabrielferreira.eventos.domain.exception.model.ErroPadraoFormularioModel;
import br.com.gabrielferreira.eventos.domain.exception.model.ErroPadraoModel;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class ErroPadraoMapper {

    public ErroPadraoModel toErroPadrao(ZonedDateTime dataAtual, Integer status, String titulo, String mensagem, String caminhoUrl){
        return ErroPadraoModel.builder()
                .dataAtual(dataAtual)
                .status(status)
                .titulo(titulo)
                .status(status)
                .mensagem(mensagem)
                .caminhoUrl(caminhoUrl)
                .build();
    }

    public ErroPadraoCamposModel toErroPadraoCampos(ZonedDateTime dataAtual, Integer status, String titulo, String mensagem, String caminhoUrl){
        ErroPadraoCamposModel erroPadraoCamposModel = new ErroPadraoCamposModel();
        erroPadraoCamposModel.setDataAtual(dataAtual);
        erroPadraoCamposModel.setStatus(status);
        erroPadraoCamposModel.setTitulo(titulo);
        erroPadraoCamposModel.setMensagem(mensagem);
        erroPadraoCamposModel.setCaminhoUrl(caminhoUrl);
        return erroPadraoCamposModel;
    }

    public ErroPadraoFormularioModel toErroPadraoFormulario(String campo, String mensagem){
        return ErroPadraoFormularioModel.builder()
                .campo(campo)
                .mensagem(mensagem)
                .build();
    }
}
