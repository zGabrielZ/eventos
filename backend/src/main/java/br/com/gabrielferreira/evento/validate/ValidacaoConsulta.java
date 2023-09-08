package br.com.gabrielferreira.evento.validate;

import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.model.AbstractModelConsulta;
import br.com.gabrielferreira.evento.model.ModelConsulta;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValidacaoConsulta {

    private ValidacaoConsulta(){}

    public static Pageable validarOrderBy(Pageable pageable, ModelConsulta consulta){
        List<Sort.Order> sorts = pageable.getSort().stream().collect(Collectors.toList());

        boolean isHouveMudanca = false;
        for (Sort.Order sort : sorts) {
            String propriedade = sort.getProperty();
            validarPropriedadesRepetidos(pageable, propriedade);

            AbstractModelConsulta consultaEncontrada = consulta.getAbstractsModelsConsultas()
                    .stream().filter(c -> c.getAtributoDto().equals(propriedade)).findFirst().orElse(null);
            if(consultaEncontrada == null){
                throw new MsgException(String.format("A propriedade informada %s não existe", propriedade));
            }

            if(!consultaEncontrada.getAtributoEntidade().equals(propriedade)){
                isHouveMudanca = true;
                Sort.Order novoSort = new Sort.Order(sort.getDirection(), consultaEncontrada.getAtributoEntidade());
                sorts.set(sorts.indexOf(sort), novoSort);
            }
        }

        if(isHouveMudanca){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
        }

        return pageable;
    }

    private static void validarPropriedadesRepetidos(Pageable pageable, String propriedade){
        List<String> propriedades = pageable.getSort().stream().map(Sort.Order::getProperty).toList();
        int duplicados = Collections.frequency(propriedades, propriedade);
        if(duplicados > 1){
            throw new MsgException(String.format("A propriedade informada %s está sendo informada mais de uma vez", propriedade));
        }
    }
}
