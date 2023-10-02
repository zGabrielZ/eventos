package br.com.gabrielferreira.evento.factory.domain;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.entity.Evento;
import org.springframework.data.domain.Page;

import static br.com.gabrielferreira.evento.factory.domain.CidadeDomainFactory.*;

public class EventoDomainFactory {

    private EventoDomainFactory(){}

    public static EventoDomain createEventoDomain(EventoRequestDTO eventoRequestDTO){
        if(eventoRequestDTO != null){
            return EventoDomain.builder()
                    .nome(eventoRequestDTO.getNome())
                    .dataEvento(eventoRequestDTO.getData())
                    .url(eventoRequestDTO.getUrl())
                    .build();
        }
        return null;
    }

    public static void updateEventoDomain(EventoDomain eventoDomainEncontrado, EventoDomain eventoDomainUpdate){
        if(eventoDomainEncontrado != null && eventoDomainUpdate != null){
            eventoDomainEncontrado.setNome(eventoDomainUpdate.getNome());
            eventoDomainEncontrado.setDataEvento(eventoDomainUpdate.getDataEvento());
            eventoDomainEncontrado.setUrl(eventoDomainUpdate.getUrl());
        }
    }

    public static EventoDomain toEventoDomain(Evento evento){
        if(evento != null){
            return EventoDomain.builder()
                    .id(evento.getId())
                    .nome(evento.getNome())
                    .dataEvento(evento.getDataEvento())
                    .url(evento.getUrl())
                    .cidade(toCidadeDomain(evento.getCidade()))
                    .createdAt(evento.getCreatedAt())
                    .updatedAt(evento.getUpdatedAt())
                    .build();
        }
        return null;
    }

    public static Page<EventoDomain> toEventosDomains(Page<Evento> eventos){
        return eventos.map(EventoDomainFactory::toEventoDomain);
    }
}
