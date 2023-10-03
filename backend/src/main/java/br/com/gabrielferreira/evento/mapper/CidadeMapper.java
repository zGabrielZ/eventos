package br.com.gabrielferreira.evento.mapper;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;
import br.com.gabrielferreira.evento.entity.Cidade;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CidadeMapper {

    CidadeMapper INSTANCE = Mappers.getMapper(CidadeMapper.class);

    CidadeDomain toCidadeDomain(Cidade cidade);

    @InheritInverseConfiguration
    CidadeResponseDTO toCidadeResponseDto(CidadeDomain cidadeDomain);

    List<CidadeDomain> toCidadesDomains(List<Cidade> cidades);

    @InheritInverseConfiguration
    List<CidadeResponseDTO> toCidadesResponsesDtos(List<CidadeDomain> cidadeDomains);
}
