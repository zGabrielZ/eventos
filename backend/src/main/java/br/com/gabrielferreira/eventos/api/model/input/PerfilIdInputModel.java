package br.com.gabrielferreira.eventos.api.model.input;

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

    @NotNull
    private Long id;
}
