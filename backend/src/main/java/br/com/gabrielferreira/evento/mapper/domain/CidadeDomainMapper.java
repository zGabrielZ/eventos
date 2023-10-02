package br.com.gabrielferreira.evento.mapper.domain;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CidadeDomainMapper {

    CidadeDomain toCidadeDomain(Cidade cidade);

    List<CidadeDomain> toCidadesDomains(List<Cidade> cidades);
}
