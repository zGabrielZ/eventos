package br.com.gabrielferreira.evento.factory.dto;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.dto.response.UsuarioResponseDTO;

import static br.com.gabrielferreira.evento.factory.dto.PerfilDTOFactory.*;

public class UsuarioDTOFactory {

    private UsuarioDTOFactory(){}

    public static UsuarioResponseDTO toUsuarioResponseDto(UsuarioDomain usuarioDomain){
        if(usuarioDomain != null){
            return new UsuarioResponseDTO(usuarioDomain.getId(), usuarioDomain.getNome(), usuarioDomain.getEmail(), toPerfisResponseDtos(usuarioDomain.getPerfis()), usuarioDomain.getCreatedAt(), usuarioDomain.getUpdatedAt());
        }
        return null;
    }
}
