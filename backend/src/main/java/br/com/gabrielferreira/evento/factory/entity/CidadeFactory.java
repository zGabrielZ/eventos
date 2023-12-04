package br.com.gabrielferreira.evento.factory.entity;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;

public class CidadeFactory {

    private CidadeFactory(){}

    public static Cidade toCreateCidade(CidadeDomain cidadeDomain){
        if(cidadeDomain != null){
            return Cidade.builder()
                    .nome(cidadeDomain.getNome())
                    .codigo(cidadeDomain.getCodigo())
                    .build();
        }
        return null;
    }

    public static Cidade toCidade(CidadeDomain cidadeDomain){
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
