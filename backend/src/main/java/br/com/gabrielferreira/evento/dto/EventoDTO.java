package br.com.gabrielferreira.evento.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public record EventoDTO(Long id, String nome, LocalDate data, String url, CidadeDTO cidade, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
}
