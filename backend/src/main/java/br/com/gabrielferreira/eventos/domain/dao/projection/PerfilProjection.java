package br.com.gabrielferreira.eventos.domain.dao.projection;

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
public class PerfilProjection implements Serializable {

    @Serial
    private static final long serialVersionUID = 3339416334050523822L;

    private Long id;

    private String descricao;

    private String autoriedade;
}
