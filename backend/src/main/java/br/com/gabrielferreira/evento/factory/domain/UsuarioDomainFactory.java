package br.com.gabrielferreira.evento.factory.domain;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.dto.request.UsuarioResquestDTO;
import br.com.gabrielferreira.evento.entity.Usuario;

import static br.com.gabrielferreira.evento.utils.DataUtils.*;
import static br.com.gabrielferreira.evento.factory.domain.PerfilDomainFactory.*;

public class UsuarioDomainFactory {

    private UsuarioDomainFactory(){}

    public static UsuarioDomain toCreateUsuarioDomain(UsuarioResquestDTO usuarioResquestDTO){
        if(usuarioResquestDTO != null){
            return UsuarioDomain.builder()
                    .nome(usuarioResquestDTO.getNome())
                    .email(usuarioResquestDTO.getEmail())
                    .senha(usuarioResquestDTO.getSenha())
                    .perfis(toCreatePerfisDomains(usuarioResquestDTO.getPerfis()))
                    .build();
        }
        return null;
    }

    public static UsuarioDomain toUsuarioDomain(Usuario usuario){
        if(usuario != null){
            return UsuarioDomain.builder()
                    .id(usuario.getId())
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .senha(usuario.getSenha())
                    .perfis(toPerfisDomains(usuario.getPerfis()))
                    .createdAt(toFusoPadraoSistema(usuario.getCreatedAt()))
                    .updatedAt(toFusoPadraoSistema(usuario.getUpdatedAt()))
                    .build();
        }
        return null;
    }
}
