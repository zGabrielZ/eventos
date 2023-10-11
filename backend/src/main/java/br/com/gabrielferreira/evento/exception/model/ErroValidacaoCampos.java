package br.com.gabrielferreira.evento.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErroValidacaoCampos extends ErroPadrao {

    @Serial
    private static final long serialVersionUID = 5648084316071390761L;

    private List<ErroFormulario> erroFormularios = new ArrayList<>();

    public void addErrosFormularios(ErroFormulario erroFormulario){
        this.erroFormularios.add(erroFormulario);
    }
}
