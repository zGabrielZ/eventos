package br.com.gabrielferreira.evento.mapper.filters;

import br.com.gabrielferreira.evento.dto.params.EventoParamsDTO;
import br.com.gabrielferreira.evento.repository.filter.EventoFilters;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventoFiltersMapper {

    EventoFilters toEventoFilters(EventoParamsDTO eventoParamsDTO);
}
