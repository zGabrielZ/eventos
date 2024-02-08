package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.EventoModel;
import br.com.gabrielferreira.eventos.api.model.EventoResumidoModel;
import br.com.gabrielferreira.eventos.api.model.input.EventoInputModel;
import br.com.gabrielferreira.eventos.api.model.params.EventoParamsModel;
import br.com.gabrielferreira.eventos.domain.dao.filter.EventoFilterModel;
import br.com.gabrielferreira.eventos.domain.dao.projection.EventoProjection;
import br.com.gabrielferreira.eventos.domain.model.Evento;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {ObjectMapper.class, CidadeMapper.class, UsuarioMapper.class})
public interface EventoMapper {

    @Mapping(target = "dataEvento", source = "data")
    Evento toEvento(EventoInputModel eventoInputModel);

    @Mapping(target = "data", source = "dataEvento")
    @Mapping(target = "dataCadastro", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    EventoModel toEventoModel(Evento evento);

    @BeanMapping(builder = @Builder( disableBuilder = true ))
    @Mapping(target = "cidade.id", source = "idCidade")
    @Mapping(target = "cidade.cep", source = "cep")
    @Mapping(target = "cidade.logradouro", source = "logradouro")
    @Mapping(target = "cidade.complemento", source = "complemento")
    @Mapping(target = "cidade.bairro", source = "bairro")
    @Mapping(target = "cidade.localidade", source = "localidade")
    @Mapping(target = "cidade.uf", source = "uf")
    @Mapping(target = "cidade.dataCadastro", source = "dataCadastroCidade", qualifiedByName = "formatData")
    @Mapping(target = "cidade.dataAtualizacao", source = "dataAtualizacaoCidade", qualifiedByName = "formatData")
    @Mapping(target = "dataCadastro", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    EventoResumidoModel toEventoResumidoModel(EventoProjection eventoProjection);

    default Page<EventoResumidoModel> toEventosResumidosModels(Page<EventoProjection> eventoProjections){
        return eventoProjections.map(this::toEventoResumidoModel);
    }

    EventoFilterModel toEventoFilterModel(EventoParamsModel eventoParamsModel);

}
