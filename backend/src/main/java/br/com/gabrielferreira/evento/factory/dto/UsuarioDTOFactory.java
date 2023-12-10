package br.com.gabrielferreira.evento.factory.dto;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.dto.response.UsuarioResponseDTO;
import org.springframework.data.domain.Page;

import static br.com.gabrielferreira.evento.factory.dto.PerfilDTOFactory.*;

public class UsuarioDTOFactory {

    private UsuarioDTOFactory(){}

    public static UsuarioResponseDTO toUsuarioResponseDto(UsuarioDomain usuarioDomain){
        if(usuarioDomain != null){
            return new UsuarioResponseDTO(usuarioDomain.getId(), usuarioDomain.getNome(), usuarioDomain.getEmail(), toPerfisResponseDtos(usuarioDomain.getPerfis()), usuarioDomain.getCreatedAt(), usuarioDomain.getUpdatedAt());
        }
        return null;
    }

    public static Page<UsuarioResponseDTO> toUsuariosResponsesDtos(Page<UsuarioDomain> usuarioDomains){
        return usuarioDomains.map(UsuarioDTOFactory::toUsuarioResponseDto);
    }
}
