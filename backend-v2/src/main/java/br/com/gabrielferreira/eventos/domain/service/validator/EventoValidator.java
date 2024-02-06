package br.com.gabrielferreira.eventos.domain.service.validator;

import br.com.gabrielferreira.eventos.domain.model.Evento;
import org.springframework.stereotype.Component;

@Component
public class EventoValidator {

    public void validarCampos(Evento evento){
        evento.setNome(evento.getNome().trim());
        evento.setUrl(evento.getUrl().trim());
    }
}
