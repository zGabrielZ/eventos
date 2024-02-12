package br.com.gabrielferreira.eventos.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.ZonedDateTime;

import static br.com.gabrielferreira.eventos.common.utils.DataUtils.toFusoPadraoSistema;

@Mapper(componentModel = "spring")
public interface ObjectMapper {

    @Named("formatData")
    default ZonedDateTime formatDate(ZonedDateTime data){
        return toFusoPadraoSistema(data);
    }
}
