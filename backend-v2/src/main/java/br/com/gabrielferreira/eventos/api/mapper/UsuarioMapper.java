package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.UsuarioModel;
import br.com.gabrielferreira.eventos.api.model.input.UsuarioInputModel;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.eventos.common.utils.DataUtils.*;

@Mapper(componentModel = "spring", uses = PerfilMapper.class)
public interface UsuarioMapper {

    Usuario toUsuario(UsuarioInputModel usuarioInputModel);

    @Mapping(target = "dataCadastro", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    UsuarioModel toUsuarioModel(Usuario usuario);

    @Named("formatData")
    default ZonedDateTime formatDate(ZonedDateTime data){
        return toFusoPadraoSistema(data);
    }
}
