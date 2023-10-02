package br.com.gabrielferreira.evento.mapper.domain;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.entity.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface EventoDomainMapper {

    @Mapping(source = "data", target = "dataEvento")
    EventoDomain toEventoDomain(EventoRequestDTO eventoRequestDTO);

    EventoDomain toEventoDomain(Evento evento);

    default Page<EventoDomain> toEventosDomains(Page<Evento> eventos){
        return eventos.map(this::toEventoDomain);
    }

    default EventoDomain toEventoDomain(Long id, EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomain = toEventoDomain(eventoRequestDTO);
        eventoDomain.setId(id);
        return eventoDomain;
    }

    default void updateEventoDomain(EventoDomain eventoDomainEncontrado, EventoDomain eventoDomainUpdate){
        if(eventoDomainEncontrado != null && eventoDomainUpdate != null){
            eventoDomainEncontrado.setNome(eventoDomainUpdate.getNome());
            eventoDomainEncontrado.setDataEvento(eventoDomainUpdate.getDataEvento());
            eventoDomainEncontrado.setUrl(eventoDomainUpdate.getUrl());
        }
    }
}
