package br.com.gabrielferreira.evento.factory.domain.v2;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;

import java.util.List;

public class CidadeDomainFactory {

    private CidadeDomainFactory(){}

    public static CidadeDomain toCidadeDomain(Cidade cidade){
        if(cidade != null){
            return CidadeDomain.builder()
                    .id(cidade.getId())
                    .nome(cidade.getNome())
                    .codigo(cidade.getCodigo())
                    .build();
        }
        return null;
    }

    public static List<CidadeDomain> toCidadesDomains(List<Cidade> cidades){
        return cidades.stream().map(CidadeDomainFactory::toCidadeDomain).toList();
    }
}
