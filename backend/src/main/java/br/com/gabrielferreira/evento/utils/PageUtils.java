package br.com.gabrielferreira.evento.utils;

import br.com.gabrielferreira.evento.exception.MsgException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class PageUtils {

    private PageUtils(){}

    public static void validarPropriedadeInformada(Sort sorts, Class<?> classe){
        if(!sorts.isEmpty()){
            List<String> propriedadesInformadas = sorts.stream().map(Sort.Order::getProperty).toList();
            List<String> campos = listarAtributosRecursivamente(new ArrayList<>(), "", classe);

            propriedadesInformadas.forEach(propriedadeInformada -> {
                if(!campos.contains(propriedadeInformada)){
                    throw new MsgException(String.format("A propriedade informada %s não existe", propriedadeInformada));
                }
            });
        }
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

    private static List<String> listarAtributosRecursivamente(List<String> campos, String prefixo, Class<?> classe) {
        for (Field campo : classe.getDeclaredFields()) {
            String nomeCampo = prefixo.concat(campo.getName());
            campos.add(nomeCampo);

            if(!campo.getType().isPrimitive() && (!campo.getType().getName().startsWith("java."))){
                campos.remove(nomeCampo);
                listarAtributosRecursivamente(campos, nomeCampo.concat("."), campo.getType());
            } else if(List.class.isAssignableFrom(campo.getType())){
                Type genericType = campo.getGenericType();
                if (genericType instanceof ParameterizedType parameterizedType) {
                    Type elementType = parameterizedType.getActualTypeArguments()[0];
                    if (elementType instanceof Class<?> classeList) {
                        campos.remove(nomeCampo);
                        listarAtributosRecursivamente(campos, nomeCampo.concat("."), classeList);
                    }
                }
            }
        }
        return campos;
    }
}
