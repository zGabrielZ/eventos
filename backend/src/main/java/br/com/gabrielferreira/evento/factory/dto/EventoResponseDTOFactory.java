package br.com.gabrielferreira.evento.factory.dto;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventoResponseDTOFactory {

    private final CidadeResponseDTOFactory cidadeResponseDTOFactory;

    public EventoResponseDTO toEventoDto(EventoDomain eventoDomain){
        if(eventoDomain != null){
            return new EventoResponseDTO(eventoDomain.getId(), eventoDomain.getNome(), eventoDomain.getDataEvento(),
                    eventoDomain.getUrl(), cidadeResponseDTOFactory.toCidadeResponseDto(eventoDomain.getCidade()),
                    eventoDomain.getCreatedAt(), eventoDomain.getUpdatedAt());
        }
        return null;
    }

    public Page<EventoResponseDTO> toEventosDtos(Page<EventoDomain> eventosDomains){
        return eventosDomains.map(this::toEventoDto);
    }
}
