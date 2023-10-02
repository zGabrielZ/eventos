package br.com.gabrielferreira.evento.factory.filters;

import br.com.gabrielferreira.evento.dto.params.EventoParamsDTO;
import br.com.gabrielferreira.evento.repository.filter.EventoFilters;

public class EventoFiltersFactory {

    private EventoFiltersFactory(){}

    public static EventoFilters createEventoFilters(EventoParamsDTO eventoParamsDTO){
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
