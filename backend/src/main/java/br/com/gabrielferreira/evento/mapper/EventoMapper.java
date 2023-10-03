package br.com.gabrielferreira.evento.mapper;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.params.EventoParamsDTO;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import br.com.gabrielferreira.evento.entity.Evento;
import br.com.gabrielferreira.evento.repository.filter.EventoFilters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = CidadeMapper.class)
public interface EventoMapper {

    EventoMapper INSTANCE = Mappers.getMapper(EventoMapper.class);

    Evento toEvento(EventoDomain eventoDomain);

    EventoDomain toEventoDomain(Evento evento);

    @Mapping(source = "data", target = "dataEvento")
    EventoDomain toEventoDomain(EventoRequestDTO eventoRequestDTO);

    @Mapping(source = "dataEvento", target = "data")
    EventoResponseDTO toEventoResponseDto(EventoDomain eventoDomain);

    EventoFilters toEventoFilters(EventoParamsDTO eventoParamsDTO);

    default Page<EventoDomain> toEventosDomains(Page<Evento> eventos){
        return eventos.map(this::toEventoDomain);
    }

    default EventoDomain toEventoDomain(Long id, EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomain = toEventoDomain(eventoRequestDTO);
        eventoDomain.setId(id);
        return eventoDomain;
    }

    default Page<EventoResponseDTO> toEventosResponsesDto(Page<EventoDomain> eventoDomains){
        return eventoDomains.map(this::toEventoResponseDto);
    }
}
