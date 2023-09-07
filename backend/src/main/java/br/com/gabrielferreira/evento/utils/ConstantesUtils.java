package br.com.gabrielferreira.evento.utils;

import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.model.AbstractModelConsulta;
import java.util.Collections;
import java.util.List;

public class ConstantesUtils {

    private ConstantesUtils(){}

    public static void validarListaRepetidaString(List<String> strings, String string, String msgValidacao){
        int duplicados = Collections.frequency(strings, string);
        if(duplicados > 1){
            throw new MsgException(msgValidacao);
        }
    }

    public static AbstractModelConsulta buscarModelConsultaPorPropriedade(List<AbstractModelConsulta> consultas, String propriedade){
        return consultas.stream().filter(c -> c.getAtributoDto().equals(propriedade)).findFirst().orElse(null);
    }

    public static void validarPropriedadeNaoEncontrada(AbstractModelConsulta consulta, String propriedade){
        if(consulta == null){
            throw new MsgException(String.format("A propriedade informada %s não existe", propriedade));
        }
    }
}
