package br.com.gabrielferreira.evento.dto.response.factory;

import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;
import br.com.gabrielferreira.evento.entity.Cidade;

import java.util.List;

public class CidadeResponseDTOFactory {

    private CidadeResponseDTOFactory(){}

    public static CidadeResponseDTO toCidadeResponseDto(Cidade cidade){
        if(cidade != null){
            return new CidadeResponseDTO(cidade.getId(), cidade.getNome(), cidade.getCodigo());
        }
        return null;
    }

    public static List<CidadeResponseDTO> toCidadesResponsesDtos(List<Cidade> cidades){
        return cidades.stream().map(CidadeResponseDTOFactory::toCidadeResponseDto).toList();
    }
}
