package br.com.gabrielferreira.evento.dto.request;

import jakarta.validation.constraints.NotNull;
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
public class PerfilIdResquestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8392496183140825629L;

    @NotNull(message = "O id do perfil não pode ser vazio")
    private Long id;
}
