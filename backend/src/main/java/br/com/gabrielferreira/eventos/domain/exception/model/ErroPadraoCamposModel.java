package br.com.gabrielferreira.eventos.domain.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErroPadraoCamposModel extends ErroPadraoModel {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<ErroPadraoFormularioModel> campos = new ArrayList<>();
}
