package br.com.gabrielferreira.eventos.domain.service.validator;

import br.com.gabrielferreira.eventos.domain.exception.RegraDeNegocioException;
import org.springframework.stereotype.Component;

import static br.com.gabrielferreira.eventos.common.utils.ConstantesUtils.*;

@Component
public class EnderecoValidator {

    public void validarCep(String cep){
        if(cep.length() != 8){
            throw new RegraDeNegocioException("O cep tem que ter no máximo de 8 caracteres");
        }

        if(!isNumerico(cep)){
            throw new RegraDeNegocioException("O cep tem que ter somente dígitos");
        }
    }
}
