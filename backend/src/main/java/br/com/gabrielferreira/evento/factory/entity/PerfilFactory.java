package br.com.gabrielferreira.evento.factory.entity;

import br.com.gabrielferreira.evento.domain.PerfilDomain;
import br.com.gabrielferreira.evento.entity.Perfil;

import java.util.List;

public class PerfilFactory {

    private PerfilFactory(){}

    public static Perfil toPerfil(PerfilDomain perfilDomain){
        if(perfilDomain != null){
            return Perfil.builder()
                    .id(perfilDomain.getId())
                    .descricao(perfilDomain.getDescricao())
                    .tipo(perfilDomain.getTipo())
                    .build();
        }
        return null;
    }

    public static List<Perfil> toPerfis(List<PerfilDomain> perfis){
        return perfis.stream().map(PerfilFactory::toPerfil).toList();
    }
}
