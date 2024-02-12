package br.com.gabrielferreira.eventos.domain.dao.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerfilFilterModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -1949955301200916008L;

    private Long id;

    private String descricao;

    private String autoriedade;

    public boolean isIdExistente(){
        return id != null;
    }

    public boolean isDescricaoExistente(){
        return StringUtils.isNotBlank(descricao);
    }

    public boolean isAutoriedadeExistente(){
        return StringUtils.isNotBlank(autoriedade);
    }
}
