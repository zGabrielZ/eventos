package br.com.gabrielferreira.eventos.domain.service.security;

import br.com.gabrielferreira.eventos.domain.exception.ForbiddenException;
import br.com.gabrielferreira.eventos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.eventos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.eventos.domain.exception.UnauthorizedException;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

import static br.com.gabrielferreira.eventos.common.utils.ConstantesUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioAutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buscarUsuarioPorEmail(username);
    }

    private Usuario buscarUsuarioPorEmail(String email){
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarUsuarioPorEmail(email);
        if(usuarioOpt.isEmpty()){
            log.warn("Usuário do e-mail {} não encontrado", email);
            throw new NaoEncontradoException(String.format("E-mail %s não encontrado", email));
        }
        log.info("Usuário do e-mail {} encontrado", email);
        return usuarioOpt.get();
    }

    public Usuario buscarUsuarioPorId(Long id){
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(id);
        if(usuarioOpt.isEmpty()){
            log.warn("Usuário do id {} não encontrado", id);
            throw new NaoEncontradoException("Usuário não encontrado");
        }
        log.info("Usuário do id {} encontrado", id);
        return usuarioOpt.get();
    }

    public Usuario usuarioAutenticado(){
        try {
            return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e){
            log.error("Usuário inválido {}", e.getMessage());
            throw new UnauthorizedException("Usuário inválido");
        }
    }

    public void validarAdminOuProprioUsuario(Long idUsuario){
        Usuario usuario = usuarioAutenticado();
        if(!usuario.getId().equals(idUsuario) && usuario.isNaoTemPerfil(ROLE_ADMIN)){
            throw new ForbiddenException(MSG_FORBIDDEN);
        }
    }

    public void validarAdmin(){
        Usuario usuario = usuarioAutenticado();
        if(usuario.isNaoTemPerfil(ROLE_ADMIN)){
            throw new ForbiddenException(MSG_FORBIDDEN);
        }
    }

    public void validarAdminExclusao(Long idUsuario){
        Usuario usuario = usuarioAutenticado();
        if(usuario.isNaoTemPerfil(ROLE_ADMIN)){
            throw new ForbiddenException(MSG_FORBIDDEN);
        }

        if(usuario.getId().equals(idUsuario)){
            throw new RegraDeNegocioException("Você não pode excluir a sua própria conta no sistema");
        }
    }
}
