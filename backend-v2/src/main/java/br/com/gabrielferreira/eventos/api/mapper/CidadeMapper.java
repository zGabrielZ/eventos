package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.CidadeModel;
import br.com.gabrielferreira.eventos.domain.model.Cidade;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ObjectMapper.class)
public interface CidadeMapper {

    @BeanMapping(builder = @Builder( disableBuilder = true ))
    @Mapping(target = "dataCadastro", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    CidadeModel toCidadeModel(Cidade cidade);
}
