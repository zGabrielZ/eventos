package br.com.gabrielferreira.eventos.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class PerfilIdInputModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -8761893198382165113L;

    @Schema(description = "Id do perfil", example = "1")
    @NotNull
    private Long id;
}
