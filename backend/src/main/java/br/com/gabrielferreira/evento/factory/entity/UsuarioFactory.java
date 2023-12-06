package br.com.gabrielferreira.evento.factory.entity;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.entity.Usuario;

import static br.com.gabrielferreira.evento.factory.entity.PerfilFactory.*;
import static br.com.gabrielferreira.evento.utils.DataUtils.toUtc;

public class UsuarioFactory {

    private UsuarioFactory(){}

    public static Usuario toCreateUsuario(UsuarioDomain usuarioDomain){
        if(usuarioDomain != null){
            return Usuario.builder()
                    .nome(usuarioDomain.getNome())
                    .email(usuarioDomain.getEmail())
                    .senha(usuarioDomain.getSenha())
                    .perfis(toPerfis(usuarioDomain.getPerfis()))
                    .build();
        }
        return null;
    }

    public static Usuario toUpdateUsuario(UsuarioDomain usuarioDomainEncontrado, UsuarioDomain usuarioDomainUpdate){
        if(usuarioDomainEncontrado != null && usuarioDomainUpdate != null){
            return Usuario.builder()
                    .id(usuarioDomainEncontrado.getId())
                    .nome(usuarioDomainUpdate.getNome())
                    .email(usuarioDomainUpdate.getEmail())
                    .senha(usuarioDomainEncontrado.getSenha())
                    .perfis(toPerfis(usuarioDomainUpdate.getPerfis()))
                    .createdAt(toUtc(usuarioDomainEncontrado.getCreatedAt()))
                    .build();
        }
        return null;
    }
}
