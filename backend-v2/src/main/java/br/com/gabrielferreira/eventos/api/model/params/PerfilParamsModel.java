package br.com.gabrielferreira.eventos.api.model.params;

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

    private Long id;

    @Size(min = 1, max = 255)
    private String descricao;

    @Size(min = 1, max = 255)
    private String autoriedade;
}
