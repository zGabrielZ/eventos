package br.com.gabrielferreira.evento.entities.factory;

import br.com.gabrielferreira.evento.dto.EventoInsertDTO;
import br.com.gabrielferreira.evento.entities.Cidade;
import br.com.gabrielferreira.evento.entities.Evento;

public class EventoFactory {

    private EventoFactory(){}

    public static Evento toEvento(Cidade cidade, EventoInsertDTO eventoInsertDTO){
        if(eventoInsertDTO != null){
            return Evento.builder()
                    .nome(eventoInsertDTO.getNome())
                    .dataEvento(eventoInsertDTO.getData())
                    .url(eventoInsertDTO.getUrl())
                    .cidade(cidade)
                    .build();
        }
        return null;
    }

    public static void toEvento(Cidade cidade, Evento evento, EventoInsertDTO eventoInsertDTO){
        if(evento != null && eventoInsertDTO != null){
            evento.setNome(eventoInsertDTO.getNome());
            evento.setDataEvento(eventoInsertDTO.getData());
            evento.setUrl(eventoInsertDTO.getUrl());
            evento.setCidade(cidade);
        }
    }
}
