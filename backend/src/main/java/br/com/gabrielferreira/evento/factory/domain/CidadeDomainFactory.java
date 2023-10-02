package br.com.gabrielferreira.evento.factory.domain;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeDomainFactory {

    public CidadeDomain toCidadeDomain(Cidade cidade){
        if(cidade != null){
            return CidadeDomain.builder()
                    .id(cidade.getId())
                    .nome(cidade.getNome())
                    .codigo(cidade.getCodigo())
                    .build();
        }
        return null;
    }

    public List<CidadeDomain> toCidadesDomains(List<Cidade> cidades){
        return cidades.stream().map(this::toCidadeDomain).toList();
    }
}
