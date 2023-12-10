package br.com.gabrielferreira.evento.factory.filter;

import br.com.gabrielferreira.evento.dto.params.UsuarioParamsDTO;
import br.com.gabrielferreira.evento.repository.filter.UsuarioFilters;

public class UsuarioFilterFactory {

    private UsuarioFilterFactory(){}

    public static UsuarioFilters toUsuarioFilters(UsuarioParamsDTO usuarioParamsDTO){
        if(usuarioParamsDTO != null){
            return UsuarioFilters.builder()
                    .id(usuarioParamsDTO.getId())
                    .nome(usuarioParamsDTO.getNome())
                    .email(usuarioParamsDTO.getEmail())
                    .idsPerfis(usuarioParamsDTO.getIdsPerfis())
                    .createdAt(usuarioParamsDTO.getCreatedAt())
                    .updatedAt(usuarioParamsDTO.getUpdatedAt())
                    .build();

        }
        return null;
    }
}
