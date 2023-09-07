package br.com.gabrielferreira.evento.model.factory;

import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.model.consulta.EventoDTOConsulta;
import br.com.gabrielferreira.evento.model.ModelConsulta;

public class ConsultaFactory {

    private ConsultaFactory(){}

    public static ModelConsulta criar(Class<?> clazz){
        if(EventoDTOConsulta.class.getName().equalsIgnoreCase(clazz.getName())){
            return new EventoDTOConsulta();
        }
        throw new MsgException("Ocorreu um erro ao realizar a consulta");
    }
}
