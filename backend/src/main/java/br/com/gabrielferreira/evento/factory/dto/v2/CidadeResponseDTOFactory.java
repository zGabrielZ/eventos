package br.com.gabrielferreira.evento.factory.dto.v2;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;

import java.util.List;

public class CidadeResponseDTOFactory {

    private CidadeResponseDTOFactory(){}

    public static CidadeResponseDTO toCidadeResponseDto(CidadeDomain cidadeDomain){
        if(cidadeDomain != null){
            return new CidadeResponseDTO(cidadeDomain.getId(), cidadeDomain.getNome(), cidadeDomain.getCodigo());
        }
        return null;
    }

    public static List<CidadeResponseDTO> toCidadesResponsesDtos(List<CidadeDomain> cidadeDomains){
        return cidadeDomains.stream().map(CidadeResponseDTOFactory::toCidadeResponseDto).toList();
    }
}
