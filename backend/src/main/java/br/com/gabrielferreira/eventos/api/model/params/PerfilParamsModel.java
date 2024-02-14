package br.com.gabrielferreira.eventos.api.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerfilParamsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -2563566075955472473L;

    @Schema(description = "Id do perfil", example = "1")
    private Long id;

    @Schema(description = "Descrição do perfil", example = "Cliente")
    @Size(min = 1, max = 255)
    private String descricao;

    @Schema(description = "Autoriedade do perfil, tipo do perfil", example = "ROLE_CLIENT")
    @Size(min = 1, max = 255)
    private String autoriedade;
}
