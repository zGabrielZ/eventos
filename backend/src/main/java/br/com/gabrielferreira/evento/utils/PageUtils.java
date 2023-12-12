package br.com.gabrielferreira.evento.utils;

import br.com.gabrielferreira.evento.exception.MsgException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.*;
import java.util.stream.Collectors;

public class PageUtils {

    private PageUtils(){}

    public static void validarPropriedade(List<String> propriedades, Sort sorts){
        for (Sort.Order sort : sorts) {
            String propriedade = sort.getProperty();
            if(!propriedades.contains(propriedade)){
                throw new MsgException(String.format("A propriedade informada '%s' não existe", propriedade));
            }
        }
    }

    public static List<String> propriedadesUsuario(){
        return Arrays.asList("id", "nome", "email", "createdAt", "updatedAt");
    }

    public static List<String> propriedadesEvento(){
        return Arrays.asList("id", "nome", "data", "url", "cidade.id", "cidade.nome", "cidade.codigo", "createdAt", "updatedAt");
    }

    public static Pageable validarOrderBy(Pageable pageable, Map<String, String> atributoDtoToEntity){
        List<Sort.Order> sorts = pageable.getSort().stream().collect(Collectors.toList());
        if(!sorts.isEmpty() && !atributoDtoToEntity.isEmpty()){
            boolean isPropriedadeEncontrada = false;
            for (Sort.Order sort : sorts) {
                String propriedadeEncontrada = atributoDtoToEntity.get(sort.getProperty());
                if(propriedadeEncontrada != null){
                    isPropriedadeEncontrada = true;
                    Sort.Order novoSort = new Sort.Order(sort.getDirection(), propriedadeEncontrada);
                    sorts.set(sorts.indexOf(sort), novoSort);
                }
            }

            if(isPropriedadeEncontrada){
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
            }
        }

        return pageable;
    }
}
