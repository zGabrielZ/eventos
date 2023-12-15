package br.com.gabrielferreira.evento.service.validation;

import br.com.gabrielferreira.evento.domain.PerfilDomain;
import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.repository.UsuarioRepository;
import br.com.gabrielferreira.evento.repository.projection.UsuarioProjection;
import br.com.gabrielferreira.evento.service.PerfilService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.gabrielferreira.evento.utils.ConstantesUtils.*;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    private final PerfilService perfilService;

    public void validarCampo(UsuarioDomain usuarioDomain){
        usuarioDomain.setNome(usuarioDomain.getNome().trim());
        usuarioDomain.setEmail(usuarioDomain.getEmail().trim());

        if(StringUtils.isNotBlank(usuarioDomain.getSenha())){
            boolean isEspacoEmBranco = isEspacoEmBranco(usuarioDomain.getSenha());
            if(isEspacoEmBranco){
                throw new MsgException("Não vai ser possível cadastrar este usuário pois a senha possui espaço em branco");
            }
            usuarioDomain.setSenha(usuarioDomain.getSenha().trim());
        }

        List<Long> idsPerfis = usuarioDomain.getPerfis().stream().map(PerfilDomain::getId).toList();
        idsPerfis.forEach(idPerfil -> {
            int duplicados = Collections.frequency(idsPerfis, idPerfil);

            if(duplicados > 1){
                throw new MsgException("Não vai ser possível cadastrar este usuário pois tem perfis duplicados ou mais de duplicados");
            }
        });
    }

    public void validarSenha(String senha){
        if(!isPossuiCaracteresEspecias(senha)){
            throw new MsgException("A senha informada tem que ter pelo menos uma caractere especial");
        }

        if(!isPossuiCaractereMaiusculas(senha)){
            throw new MsgException("A senha informada tem que ter pelo menos uma caractere maiúsculas");
        }

        if(!isPossuiCaractereMinusculas(senha)){
            throw new MsgException("A senha informada tem que ter pelo menos uma caractere minúsculas");
        }

        if(!isPossuiCaractereDigito(senha)){
            throw new MsgException("A senha informada tem que ter pelo menos um caractere dígito");
        }
    }

    public void validarEmail(UsuarioDomain usuarioDomain){
        Optional<UsuarioProjection> usuarioEncontrado = usuarioRepository.existeEmailUsuario(usuarioDomain.getEmail());
        if(usuarioDomain.getId() == null && usuarioEncontrado.isPresent()){
            throw new MsgException(String.format("Não vai ser possível cadastrar este usuário pois o email '%s' já foi cadastrado", usuarioDomain.getEmail()));
        } else if(usuarioDomain.getId() != null && usuarioEncontrado.isPresent() && !usuarioDomain.getId().equals(usuarioEncontrado.get().getId())){
            throw new MsgException(String.format("Não vai ser possível atualizar este usuário pois o email '%s' já foi cadastrado", usuarioDomain.getEmail()));
        }
    }

    public void validarPerfil(UsuarioDomain usuarioDomain){
        List<PerfilDomain> perfis = usuarioDomain.getPerfis();
        List<PerfilDomain> novosPerfis = new ArrayList<>();
        perfis.forEach(perfil -> {
            PerfilDomain perfilDomain = perfilService.buscarPerfilPorId(perfil.getId());
            novosPerfis.add(perfilDomain);
        });

        usuarioDomain.getPerfis().clear();
        usuarioDomain.setPerfis(novosPerfis);
    }
}
