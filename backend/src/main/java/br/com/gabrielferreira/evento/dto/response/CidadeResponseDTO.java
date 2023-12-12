package br.com.gabrielferreira.evento.dto.response;

import java.time.ZonedDateTime;

public record CidadeResponseDTO(Long id, String nome, String codigo, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
}
