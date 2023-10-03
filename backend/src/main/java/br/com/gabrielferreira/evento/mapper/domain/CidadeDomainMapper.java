package br.com.gabrielferreira.evento.mapper.domain;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CidadeDomainMapper {

    CidadeDomainMapper INSTANCE = Mappers.getMapper(CidadeDomainMapper.class);

    CidadeDomain toCidadeDomain(Cidade cidade);

    List<CidadeDomain> toCidadesDomains(List<Cidade> cidades);
}
