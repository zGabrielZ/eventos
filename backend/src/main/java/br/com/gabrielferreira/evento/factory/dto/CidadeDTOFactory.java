package br.com.gabrielferreira.evento.factory.dto;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;

import java.util.List;

public class CidadeDTOFactory {

    private CidadeDTOFactory(){}

    public static CidadeResponseDTO toCidadeResponseDto(CidadeDomain cidadeDomain){
        if(cidadeDomain != null){
            return new CidadeResponseDTO(cidadeDomain.getId(), cidadeDomain.getNome(), cidadeDomain.getCodigo(), cidadeDomain.getCreatedAt(), cidadeDomain.getUpdatedAt());
        }
        return null;
    }

    public static List<CidadeResponseDTO> toCidadesResponsesDtos(List<CidadeDomain> cidadeDomains){
        return cidadeDomains.stream().map(CidadeDTOFactory::toCidadeResponseDto).toList();
    }
}
