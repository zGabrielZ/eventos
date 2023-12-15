package br.com.gabrielferreira.evento.dto.response;

import java.time.ZonedDateTime;

public record LoginResponseDTO(Long idUsuario, String tipo, String token, ZonedDateTime dataInicio, ZonedDateTime dataFim) {
}
