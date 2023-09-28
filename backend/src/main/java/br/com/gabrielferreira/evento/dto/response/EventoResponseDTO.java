package br.com.gabrielferreira.evento.dto.response;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public record EventoResponseDTO(Long id, String nome, LocalDate data, String url, CidadeResponseDTO cidade, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
}
