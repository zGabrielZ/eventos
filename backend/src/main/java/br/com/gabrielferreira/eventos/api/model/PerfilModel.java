package br.com.gabrielferreira.eventos.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfilModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 150854081687212908L;

    @Schema(description = "Id do perfil", example = "1")
    private Long id;

    @Schema(description = "Descrição do perfil", example = "Cliente")
    private String descricao;

    @Schema(description = "Autoriedade do perfil, tipo do perfil", example = "ROLE_CLIENT")
    private String autoriedade;
}
