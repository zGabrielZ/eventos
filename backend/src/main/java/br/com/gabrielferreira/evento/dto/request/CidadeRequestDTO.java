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
public class CidadeRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 958714108846456044L;

    @NotNull(message = "O id da cidade não pode ser vazio")
    private Long id;
}
