package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.PerfilModel;
import br.com.gabrielferreira.eventos.domain.model.Perfil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PerfilMapper {

    public PerfilModel toPerfilModel(Perfil perfil){
        return PerfilModel.builder()
                .id(perfil.getId())
                .descricao(perfil.getDescricao())
                .autoriedade(perfil.getAutoriedade())
                .build();
    }

    public List<PerfilModel> toPerfisModels(List<Perfil> perfis){
        return perfis.stream().map(this::toPerfilModel).toList();
    }
}
