package br.com.gabrielferreira.evento.factory.domain;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.request.CidadeIdRequestDTO;
import br.com.gabrielferreira.evento.dto.request.CidadeRequestDTO;
import br.com.gabrielferreira.evento.entity.Cidade;
import java.util.List;

import static br.com.gabrielferreira.evento.utils.DataUtils.*;

public class CidadeDomainFactory {

    private CidadeDomainFactory(){}

    public static CidadeDomain toCreateCidadeDomain(CidadeRequestDTO cidadeRequestDTO){
        if(cidadeRequestDTO != null){
            return CidadeDomain.builder()
                    .nome(cidadeRequestDTO.getNome())
                    .codigo(cidadeRequestDTO.getCodigo())
                    .build();
        }
        return null;
    }

    public static CidadeDomain toCidadeDomain(CidadeIdRequestDTO cidadeIdRequestDTO){
        if(cidadeIdRequestDTO != null){
            return CidadeDomain.builder()
                    .id(cidadeIdRequestDTO.getId())
                    .build();
        }
        return null;
    }

    public static CidadeDomain toCidadeDomain(Cidade cidade){
        if(cidade != null){
            return CidadeDomain.builder()
                    .id(cidade.getId())
                    .nome(cidade.getNome())
                    .codigo(cidade.getCodigo())
                    .createdAt(toFusoPadraoSistema(cidade.getCreatedAt()))
                    .updatedAt(toFusoPadraoSistema(cidade.getUpdatedAt()))
                    .build();
        }
        return null;
    }

    public static List<CidadeDomain> toCidadesDomains(List<Cidade> cidades){
        return cidades.stream().map(CidadeDomainFactory::toCidadeDomain).toList();
    }
}
