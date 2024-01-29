package br.com.gabrielferreira.eventos.api.mapper;

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
}
