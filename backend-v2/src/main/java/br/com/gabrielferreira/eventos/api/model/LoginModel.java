package br.com.gabrielferreira.eventos.api.model;

import br.com.gabrielferreira.eventos.domain.service.security.model.InformacoesTokenModel;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
public class LoginModel extends InformacoesTokenModel {

    @Serial
    private static final long serialVersionUID = 7469120273635899831L;
}
