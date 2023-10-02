//package br.com.gabrielferreira.evento.dto.response.factory;
//
//import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
//import br.com.gabrielferreira.evento.entity.Evento;
//import org.springframework.data.domain.Page;
//
//import static br.com.gabrielferreira.evento.dto.response.factory.CidadeResponseDTOFactory.*;
//
//public class EventoResponseDTOFactory {
//
//    private EventoResponseDTOFactory(){}
//
//    public static EventoResponseDTO toEventoDto(Evento evento){
//        if(evento != null){
//            return new EventoResponseDTO(evento.getId(), evento.getNome(), evento.getDataEvento(), evento.getUrl(), toCidadeResponseDto(evento.getCidade()), evento.getCreatedAt(), evento.getUpdatedAt());
//        }
//        return null;
//    }
//
//    public static Page<EventoResponseDTO> toEventosDtos(Page<Evento> eventos){
//        return eventos.map(EventoResponseDTOFactory::toEventoDto);
//    }
//}
