package br.com.gabrielferreira.eventos.domain.service.validator;

import br.com.gabrielferreira.eventos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.eventos.domain.model.Perfil;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.repository.UsuarioRepository;
import br.com.gabrielferreira.eventos.domain.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static br.com.gabrielferreira.eventos.common.utils.ConstantesUtils.*;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    private final PerfilService perfilService;

    public void validarCampos(Usuario usuario){
        usuario.setNome(usuario.getNome().trim());
        usuario.setEmail(usuario.getEmail().trim());
    }

    public void validarSenha(String senha){
        if(!isPossuiCaracteresEspecias(senha)){
            throw new RegraDeNegocioException("A senha informada tem que ter pelo menos uma caractere especial");
        }

        if(!isPossuiCaractereMaiusculas(senha)){
            throw new RegraDeNegocioException("A senha informada tem que ter pelo menos uma caractere maiúsculas");
        }

        if(!isPossuiCaractereMinusculas(senha)){
            throw new RegraDeNegocioException("A senha informada tem que ter pelo menos uma caractere minúsculas");
        }

        if(!isPossuiCaractereDigito(senha)){
            throw new RegraDeNegocioException("A senha informada tem que ter pelo menos um caractere dígito");
        }
    }

    public void validarEmail(Long id, String email){
        if(isEmailExistente(id, email)){
            throw new RegraDeNegocioException(String.format("Este e-mail '%s' já foi cadastrado", email));
        }
    }

    public boolean isEmailExistente(Long id, String email){
        if(id == null){
            return usuarioRepository.buscarPorEmail(email)
                    .isPresent();
        } else {
            return usuarioRepository.buscarPorEmail(email)
                    .filter(u -> !u.getId().equals(id))
                    .isPresent();
        }
    }

    public void validarPerfil(List<Perfil> perfis){
        validarPerfilDuplicados(perfis);
        validarIdPerfil(perfis);
    }

    public void validarPerfilDuplicados(List<Perfil> perfis){
        List<Long> idsPerfis = perfis.stream().map(Perfil::getId).toList();
        idsPerfis.forEach(idPerfil -> {
            int duplicados = Collections.frequency(idsPerfis, idPerfil);

            if(duplicados > 1){
                throw new RegraDeNegocioException("Não vai ser possível cadastrar este usuário pois tem perfis duplicados ou mais de duplicados");
            }
        });
    }

    public void validarIdPerfil(List<Perfil> perfis){
        perfis.forEach(perfil -> {
            Perfil perfilEncontrado = perfilService.buscarPerfilPorId(perfil.getId());
            perfil.setDescricao(perfilEncontrado.getDescricao());
            perfil.setAutoriedade(perfilEncontrado.getAutoriedade());
        });
    }
}
