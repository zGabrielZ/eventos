package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.PerfilModel;
import br.com.gabrielferreira.eventos.api.model.params.PerfilParamsModel;
import br.com.gabrielferreira.eventos.domain.dao.filter.PerfilFilterModel;
import br.com.gabrielferreira.eventos.domain.dao.projection.PerfilProjection;
import br.com.gabrielferreira.eventos.domain.model.Perfil;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerfilMapper {

    PerfilModel toPerfilModel(Perfil perfil);

    default List<PerfilModel> toPerfisModels(List<Perfil> perfis){
        return perfis.stream().map(this::toPerfilModel).toList();
    }

    PerfilFilterModel toPerfilFilterModel(PerfilParamsModel perfilParamsModel);

    PerfilModel toPerfilModel(PerfilProjection perfilProjection);

    default Page<PerfilModel> toPerfisModels(Page<PerfilProjection> perfilProjections){
        return perfilProjections.map(this::toPerfilModel);
    }
}
