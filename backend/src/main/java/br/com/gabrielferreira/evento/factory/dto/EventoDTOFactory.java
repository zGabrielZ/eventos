package br.com.gabrielferreira.evento.factory.dto;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import org.springframework.data.domain.Page;

import static br.com.gabrielferreira.evento.factory.dto.CidadeDTOFactory.*;

public class EventoDTOFactory {

    private EventoDTOFactory(){}

    public static EventoResponseDTO toEventoResponseDto(EventoDomain eventoDomain){
        if(eventoDomain != null){
            return new EventoResponseDTO(eventoDomain.getId(), eventoDomain.getNome(), eventoDomain.getDataEvento(), eventoDomain.getUrl(),
                    toCidadeResponseDto(eventoDomain.getCidade()), eventoDomain.getCreatedAt(), eventoDomain.getUpdatedAt());
        }
        return null;
    }

    public static Page<EventoResponseDTO> toEventosResponsesDtos(Page<EventoDomain> eventoDomains){
        return eventoDomains.map(EventoDTOFactory::toEventoResponseDto);
    }
}
