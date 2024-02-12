package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.UsuarioModel;
import br.com.gabrielferreira.eventos.api.model.UsuarioResumidoModel;
import br.com.gabrielferreira.eventos.api.model.input.UsuarioInputModel;
import br.com.gabrielferreira.eventos.api.model.params.UsuarioParamsModel;
import br.com.gabrielferreira.eventos.domain.dao.filter.UsuarioFilterModel;
import br.com.gabrielferreira.eventos.domain.dao.projection.UsuarioProjection;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {PerfilMapper.class, ObjectMapper.class})
public interface UsuarioMapper {

    Usuario toUsuario(UsuarioInputModel usuarioInputModel);

    @Mapping(target = "dataCadastro", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    UsuarioModel toUsuarioModel(Usuario usuario);

    UsuarioFilterModel toUsuarioFilterModel(UsuarioParamsModel usuarioParamsModel);

    @Mapping(target = "dataCadastro", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    UsuarioResumidoModel toUsuarioResumidoModel(UsuarioProjection usuarioProjection);

    @Mapping(target = "dataCadastro", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    UsuarioResumidoModel toUsuarioResumidoModel(Usuario usuario);

    default Page<UsuarioResumidoModel> toUsuarioResumidosModels(Page<UsuarioProjection> usuarioProjections){
        return usuarioProjections.map(this::toUsuarioResumidoModel);
    }
}
