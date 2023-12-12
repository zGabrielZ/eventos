package br.com.gabrielferreira.evento.dto.response;

import java.time.ZonedDateTime;

public record UsuarioPaginacaoResponseDTO(Long id, String nome, String email, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
}
