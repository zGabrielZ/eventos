package br.com.gabrielferreira.evento.factory.dto;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import org.springframework.data.domain.Page;

public class EventoResponseDTOFactory {

    private EventoResponseDTOFactory(){}

    public static EventoResponseDTO toEventoDto(EventoDomain eventoDomain){
        if(eventoDomain != null){
            return new EventoResponseDTO(eventoDomain.getId(), eventoDomain.getNome(), eventoDomain.getDataEvento(),
                    eventoDomain.getUrl(), CidadeResponseDTOFactory.toCidadeResponseDto(eventoDomain.getCidade()),
                    eventoDomain.getCreatedAt(), eventoDomain.getUpdatedAt());
        }
        return null;
    }

    public static Page<EventoResponseDTO> toEventosDtos(Page<EventoDomain> eventosDomains){
        return eventosDomains.map(EventoResponseDTOFactory::toEventoDto);
    }
}
