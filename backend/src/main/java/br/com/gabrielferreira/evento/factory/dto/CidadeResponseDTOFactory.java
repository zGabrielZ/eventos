package br.com.gabrielferreira.evento.factory.dto;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeResponseDTOFactory {

    public CidadeResponseDTO toCidadeResponseDto(CidadeDomain cidadeDomain){
        if(cidadeDomain != null){
            return new CidadeResponseDTO(cidadeDomain.getId(), cidadeDomain.getNome(), cidadeDomain.getCodigo());
        }
        return null;
    }

    public List<CidadeResponseDTO> toCidadesResponsesDtos(List<CidadeDomain> cidadeDomains){
        return cidadeDomains.stream().map(this::toCidadeResponseDto).toList();
    }
}
