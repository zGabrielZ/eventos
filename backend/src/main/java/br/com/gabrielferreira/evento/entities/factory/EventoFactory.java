package br.com.gabrielferreira.evento.entities.factory;

import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.entities.Cidade;
import br.com.gabrielferreira.evento.entities.Evento;

public class EventoFactory {

    private EventoFactory(){}

    public static Evento toEvento(Cidade cidade, EventoRequestDTO eventoRequestDTO){
        if(eventoRequestDTO != null){
            return Evento.builder()
                    .nome(eventoRequestDTO.getNome())
                    .dataEvento(eventoRequestDTO.getData())
                    .url(eventoRequestDTO.getUrl())
                    .cidade(cidade)
                    .build();
        }
        return null;
    }

    public static void toEvento(Cidade cidade, Evento evento, EventoRequestDTO eventoRequestDTO){
        if(evento != null && eventoRequestDTO != null){
            evento.setNome(eventoRequestDTO.getNome());
            evento.setDataEvento(eventoRequestDTO.getData());
            evento.setUrl(eventoRequestDTO.getUrl());
            evento.setCidade(cidade);
        }
    }
}
