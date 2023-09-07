package br.com.gabrielferreira.evento.model.consulta;

import br.com.gabrielferreira.evento.model.AbstractModelConsulta;
import br.com.gabrielferreira.evento.model.ModelConsulta;

import java.util.ArrayList;
import java.util.List;

public class EventoDTOConsulta implements ModelConsulta {

    @Override
    public List<AbstractModelConsulta> getAbstractsModelsConsultas() {
        List<AbstractModelConsulta> abstractModelConsultas = new ArrayList<>();

        abstractModelConsultas.add(AbstractModelConsulta.builder()
                        .atributoDto("id")
                        .atributoEntidade("id")
                        .build());

        abstractModelConsultas.add(AbstractModelConsulta.builder()
                .atributoDto("nome")
                .atributoEntidade("nome")
                .build());

        abstractModelConsultas.add(AbstractModelConsulta.builder()
                .atributoDto("data")
                .atributoEntidade("dataEvento")
                .build());

        abstractModelConsultas.add(AbstractModelConsulta.builder()
                .atributoDto("url")
                .atributoEntidade("url")
                .build());

        abstractModelConsultas.add(AbstractModelConsulta.builder()
                .atributoDto("cidade.id")
                .atributoEntidade("cidade.id")
                .build());

        abstractModelConsultas.add(AbstractModelConsulta.builder()
                .atributoDto("cidade.nome")
                .atributoEntidade("cidade.nome")
                .build());

        abstractModelConsultas.add(AbstractModelConsulta.builder()
                .atributoDto("cidade.codigo")
                .atributoEntidade("cidade.codigo")
                .build());

        abstractModelConsultas.add(AbstractModelConsulta.builder()
                .atributoDto("createdAt")
                .atributoEntidade("createdAt")
                .build());

        abstractModelConsultas.add(AbstractModelConsulta.builder()
                .atributoDto("updatedAt")
                .atributoEntidade("updatedAt")
                .build());

        return abstractModelConsultas;
    }
}
