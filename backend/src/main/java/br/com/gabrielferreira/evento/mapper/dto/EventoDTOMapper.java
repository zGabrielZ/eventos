package br.com.gabrielferreira.evento.mapper.dto;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface EventoDTOMapper {

    @Mapping(source = "dataEvento", target = "data")
    EventoResponseDTO toEventoDto(EventoDomain eventoDomain);

    default Page<EventoResponseDTO> toEventosDtos(Page<EventoDomain> eventoDomains){
        return eventoDomains.map(this::toEventoDto);
    }
}
