package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.EventoModel;
import br.com.gabrielferreira.eventos.api.model.input.EventoInputModel;
import br.com.gabrielferreira.eventos.domain.model.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ObjectMapper.class, CidadeMapper.class, UsuarioMapper.class})
public interface EventoMapper {

    @Mapping(target = "dataEvento", source = "data")
    Evento toEvento(EventoInputModel eventoInputModel);

    @Mapping(target = "data", source = "dataEvento")
    @Mapping(target = "dataCadastro", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    EventoModel toEventoModel(Evento evento);
}
