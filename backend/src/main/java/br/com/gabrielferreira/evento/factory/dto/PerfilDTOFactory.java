package br.com.gabrielferreira.evento.factory.dto;

import br.com.gabrielferreira.evento.domain.PerfilDomain;
import br.com.gabrielferreira.evento.dto.response.PerfilResponseDTO;
import java.util.List;


public class PerfilDTOFactory {

    private PerfilDTOFactory(){}

    public static PerfilResponseDTO toPerfilResponseDto(PerfilDomain perfilDomain){
        if(perfilDomain != null){
            return new PerfilResponseDTO(perfilDomain.getId(), perfilDomain.getDescricao(), perfilDomain.getTipo());
        }
        return null;
    }

    public static List<PerfilResponseDTO> toPerfisResponseDtos(List<PerfilDomain> perfilDomains){
        return perfilDomains.stream().map(PerfilDTOFactory::toPerfilResponseDto).toList();
    }
}
