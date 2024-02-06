package br.com.gabrielferreira.eventos.tests;

import br.com.gabrielferreira.eventos.api.model.input.CidadeInputModel;
import br.com.gabrielferreira.eventos.api.model.input.EventoInputModel;

import java.time.LocalDate;

public class EventoFactory {

    private EventoFactory(){}

    public static EventoInputModel criarEventoInput(){
        CidadeInputModel cidadeInputModel = CidadeInputModel.builder()
                .cep("01451000")
                .build();

        return EventoInputModel.builder()
                .nome("Teste evento")
                .data(LocalDate.now().plusDays(5L))
                .url("https://www.google.com.br/")
                .cidade(cidadeInputModel)
                .build();
    }
}
