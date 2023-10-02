package br.com.gabrielferreira.evento.factory.domain;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.entity.Evento;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventoDomainFactory {

    private final CidadeDomainFactory cidadeDomainFactory;

    public EventoDomain toEventoDomain(Evento evento){
        if(evento != null){
            return EventoDomain.builder()
                    .id(evento.getId())
                    .nome(evento.getNome())
                    .dataEvento(evento.getDataEvento())
                    .url(evento.getUrl())
                    .cidade(cidadeDomainFactory.toCidadeDomain(evento.getCidade()))
                    .createdAt(evento.getCreatedAt())
                    .updatedAt(evento.getUpdatedAt())
                    .build();
        }
        return null;
    }

    public void toEventoDomain(CidadeDomain cidadeDomain, EventoDomain eventoDomain, EventoDomain eventoDomainUpdate){
        if(eventoDomain != null && eventoDomainUpdate != null){
            eventoDomain.setNome(eventoDomainUpdate.getNome());
            eventoDomain.setDataEvento(eventoDomainUpdate.getDataEvento());
            eventoDomain.setUrl(eventoDomainUpdate.getUrl());
            eventoDomain.setCidade(cidadeDomain);
        }
    }

    public Page<EventoDomain> toEventosDomains(Page<Evento> eventos){
        return eventos.map(this::toEventoDomain);
    }

    public EventoDomain toEventoDomain(EventoRequestDTO eventoRequestDTO){
        if(eventoRequestDTO != null){
            return EventoDomain.builder()
                    .nome(eventoRequestDTO.getNome())
                    .dataEvento(eventoRequestDTO.getData())
                    .url(eventoRequestDTO.getUrl())
                    .cidade(cidadeDomainFactory.toCidadeDomain(eventoRequestDTO.getCidade()))
                    .build();
        }
        return null;
    }

    public EventoDomain toEventoDomain(Long id, EventoRequestDTO eventoRequestDTO){
        if(id != null){
            EventoDomain eventoDomain = toEventoDomain(eventoRequestDTO);
            eventoDomain.setId(id);
            return eventoDomain;
        }
        return null;
    }
}
