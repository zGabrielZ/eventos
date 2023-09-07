package br.com.gabrielferreira.evento.dto.factory;

import br.com.gabrielferreira.evento.dto.EventoDTO;
import br.com.gabrielferreira.evento.entities.Evento;
import org.springframework.data.domain.Page;

import static br.com.gabrielferreira.evento.dto.factory.CidadeDTOFactory.*;

public class EventoDTOFactory {

    private EventoDTOFactory(){}

    public static EventoDTO toEventoDto(Evento evento){
        if(evento != null){
            return new EventoDTO(evento.getId(), evento.getNome(), evento.getDataEvento(), evento.getUrl(), toCidadeDto(evento.getCidade()), evento.getCreatedAt(), evento.getUpdatedAt());
        }
        return null;
    }

    public static Page<EventoDTO> toEventosDtos(Page<Evento> eventos){
        return eventos.map(EventoDTOFactory::toEventoDto);
    }
}
