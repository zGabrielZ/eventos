package br.com.gabrielferreira.evento.dto.response;

import java.time.ZonedDateTime;
import java.util.List;

public record UsuarioResponseDTO(Long id, String nome, String email, List<PerfilResponseDTO> perfis, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
}
