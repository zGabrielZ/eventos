package br.com.gabrielferreira.evento.factory.entity;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import org.springframework.stereotype.Component;

@Component
public class CidadeFactory {

    public Cidade toCidade(CidadeDomain cidadeDomain){
        if(cidadeDomain != null){
            return Cidade.builder()
                    .id(cidadeDomain.getId())
                    .nome(cidadeDomain.getNome())
                    .codigo(cidadeDomain.getCodigo())
                    .build();
        }
        return null;
    }
}
