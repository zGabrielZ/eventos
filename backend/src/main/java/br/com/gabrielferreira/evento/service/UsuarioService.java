package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.entity.Usuario;
import br.com.gabrielferreira.evento.repository.UsuarioRepository;
import br.com.gabrielferreira.evento.service.validation.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.gabrielferreira.evento.factory.entity.UsuarioFactory.*;
import static br.com.gabrielferreira.evento.factory.domain.UsuarioDomainFactory.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioValidator usuarioValidator;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UsuarioDomain cadastrarUsuario(UsuarioDomain usuarioDomain){
        usuarioValidator.validarCampo(usuarioDomain);
        usuarioValidator.validarSenha(usuarioDomain.getSenha());
        usuarioValidator.validarEmail(usuarioDomain);
        usuarioValidator.validarPerfil(usuarioDomain);

        Usuario usuario = toCreateUsuario(usuarioDomain);
        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        usuario = usuarioRepository.save(usuario);
        return toUsuarioDomain(usuario);
    }
}
