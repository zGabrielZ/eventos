package br.com.gabrielferreira.eventos.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class CidadeInputModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 8867938421901068294L;

    @Schema(description = "Cep", example = "01451000")
    @NotNull
    private String cep;

    @Schema(description = "Complemento", example = "Casa")
    @Size(min = 1, max = 155)
    private String complemento;
}
