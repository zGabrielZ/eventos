package br.com.gabrielferreira.evento.mapper.entity;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    Evento toEvento(EventoDomain eventoDomain);
}
