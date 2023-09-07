package br.com.gabrielferreira.evento.dto.factory;

import br.com.gabrielferreira.evento.dto.CidadeDTO;
import br.com.gabrielferreira.evento.entities.Cidade;

import java.util.List;

public class CidadeDTOFactory {

    private CidadeDTOFactory(){}

    public static CidadeDTO toCidadeDto(Cidade cidade){
        if(cidade != null){
            return new CidadeDTO(cidade.getId(), cidade.getNome(), cidade.getCodigo());
        }
        return null;
    }

    public static List<CidadeDTO> toCidadesDtos(List<Cidade> cidades){
        return cidades.stream().map(CidadeDTOFactory::toCidadeDto).toList();
    }
}
