package br.com.gabrielferreira.evento.mapper.dto;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CidadeDTOMapper {

    CidadeResponseDTO toCidadeDto(CidadeDomain cidadeDomain);

    List<CidadeResponseDTO> toCidadesDtos(List<CidadeDomain> cidadeDomains);

}
