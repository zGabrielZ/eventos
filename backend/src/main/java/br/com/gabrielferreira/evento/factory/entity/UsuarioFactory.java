package br.com.gabrielferreira.evento.factory.entity;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.entity.Usuario;

import static br.com.gabrielferreira.evento.factory.entity.PerfilFactory.*;

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
}
