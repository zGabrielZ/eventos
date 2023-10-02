package br.com.gabrielferreira.evento.factory.entity;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;

import static br.com.gabrielferreira.evento.factory.entity.CidadeFactory.*;

public class EventoFactory {

    private EventoFactory(){}

    public static Evento createEvento(EventoDomain eventoDomain){
        if(eventoDomain != null){
            return Evento.builder()
                    .nome(eventoDomain.getNome())
                    .dataEvento(eventoDomain.getDataEvento())
                    .url(eventoDomain.getUrl())
                    .cidade(toCidade(eventoDomain.getCidade()))
                    .build();
        }
        return null;
    }

    public static Evento updateEvento(EventoDomain eventoDomain){
        if(eventoDomain != null){
            return Evento.builder()
                    .id(eventoDomain.getId())
                    .nome(eventoDomain.getNome())
                    .dataEvento(eventoDomain.getDataEvento())
                    .url(eventoDomain.getUrl())
                    .cidade(toCidade(eventoDomain.getCidade()))
                    .createdAt(eventoDomain.getCreatedAt())
                    .updatedAt(eventoDomain.getUpdatedAt())
                    .build();
        }
        return null;
    }
}
