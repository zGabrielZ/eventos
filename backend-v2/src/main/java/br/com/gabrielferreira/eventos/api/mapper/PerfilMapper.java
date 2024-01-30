package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.PerfilModel;
import br.com.gabrielferreira.eventos.domain.model.Perfil;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerfilMapper {

    PerfilModel toPerfilModel(Perfil perfil);

    default List<PerfilModel> toPerfisModels(List<Perfil> perfis){
        return perfis.stream().map(this::toPerfilModel).toList();
    }
}
