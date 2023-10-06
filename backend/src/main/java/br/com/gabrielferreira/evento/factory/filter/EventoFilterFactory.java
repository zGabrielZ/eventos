package br.com.gabrielferreira.evento.factory.filter;

import br.com.gabrielferreira.evento.dto.params.EventoParamsDTO;
import br.com.gabrielferreira.evento.repository.filter.EventoFilters;

public class EventoFilterFactory {

    private EventoFilterFactory(){}

    public static EventoFilters toEventoFilters(EventoParamsDTO eventoParamsDTO){
        if(eventoParamsDTO != null){
            return EventoFilters.builder()
                    .id(eventoParamsDTO.getId())
                    .nome(eventoParamsDTO.getNome())
                    .data(eventoParamsDTO.getData())
                    .url(eventoParamsDTO.getUrl())
                    .idCidade(eventoParamsDTO.getIdCidade())
                    .createdAt(eventoParamsDTO.getCreatedAt())
                    .updatedAt(eventoParamsDTO.getUpdatedAt())
                    .build();

        }
        return null;
    }
}
