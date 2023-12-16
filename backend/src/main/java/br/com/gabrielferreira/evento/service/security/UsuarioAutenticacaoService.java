package br.com.gabrielferreira.evento.service.security;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.entity.Usuario;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.gabrielferreira.evento.factory.domain.UsuarioDomainFactory.toUsuarioDomain;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioAutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buscarUsuarioPorEmail(username);
    }

    private UsuarioDomain buscarUsuarioPorEmail(String email){
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorEmail(email);
        if(usuarioOpt.isEmpty()){
            log.warn("Usuário do e-mail {} não encontrado", email);
            throw new NaoEncontradoException(String.format("E-mail %s não encontrado", email));
        }
        log.info("Usuário do e-mail {} encontrado", email);
        return toUsuarioDomain(usuarioOpt.get());
    }

    public UsuarioDomain buscarUsuarioPorId(Long id){
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(id);
        if(usuarioOpt.isEmpty()){
            log.warn("Usuário do id {} não encontrado", id);
            throw new NaoEncontradoException(String.format("Id %s não encontrado", id));
        }
        log.info("Usuário do id {} encontrado", id);
        return toUsuarioDomain(usuarioOpt.get());
    }
}
