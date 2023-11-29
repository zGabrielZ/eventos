package br.com.gabrielferreira.evento.factory.entity;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;

import static br.com.gabrielferreira.evento.factory.entity.CidadeFactory.*;
import static br.com.gabrielferreira.evento.utils.DataUtils.*;

public class EventoFactory {

    private EventoFactory(){}

    public static Evento toCreateEvento(EventoDomain eventoDomain){
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

    public static Evento toUpdateEvento(EventoDomain eventoDomainEncontrado, EventoDomain eventoDomainUpdate){
        if(eventoDomainEncontrado != null && eventoDomainUpdate != null){
            return Evento.builder()
                    .id(eventoDomainEncontrado.getId())
                    .nome(eventoDomainUpdate.getNome())
                    .dataEvento(eventoDomainUpdate.getDataEvento())
                    .url(eventoDomainUpdate.getUrl())
                    .cidade(toCidade(eventoDomainUpdate.getCidade()))
                    .createdAt(toUtc(eventoDomainEncontrado.getCreatedAt()))
                    .build();
        }
        return null;
    }
}
