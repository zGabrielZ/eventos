package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.entity.Usuario;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.UsuarioRepository;
import br.com.gabrielferreira.evento.repository.filter.UsuarioFilters;
import br.com.gabrielferreira.evento.service.validation.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static br.com.gabrielferreira.evento.factory.entity.UsuarioFactory.*;
import static br.com.gabrielferreira.evento.factory.domain.UsuarioDomainFactory.*;
import static br.com.gabrielferreira.evento.utils.PageUtils.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioValidator usuarioValidator;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConsultaAvancadaService consultaAvancadaService;

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

    public UsuarioDomain buscarUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado"));
        return toUsuarioDomain(usuario);
    }

    @Transactional
    public UsuarioDomain atualizarUsuario(UsuarioDomain usuarioDomain){
        usuarioValidator.validarCampo(usuarioDomain);
        usuarioValidator.validarEmail(usuarioDomain);
        usuarioValidator.validarPerfil(usuarioDomain);

        UsuarioDomain usuarioDomainEncontrado = buscarUsuarioPorId(usuarioDomain.getId());

        Usuario usuario = toUpdateUsuario(usuarioDomainEncontrado, usuarioDomain);
        usuario = usuarioRepository.save(usuario);
        return toUsuarioDomain(usuario);
    }

    @Transactional
    public void deletarUsuarioPorId(Long id){
        UsuarioDomain usuarioDomainEncontrado = buscarUsuarioPorId(id);
        usuarioRepository.deleteById(usuarioDomainEncontrado.getId());
    }

    public Page<UsuarioDomain> buscarUsuarios(UsuarioFilters usuarioFilters, Pageable pageable){
        validarPropriedade(propriedadesUsuario(), pageable.getSort());
        return consultaAvancadaService.buscarUsuarios(usuarioFilters, pageable, new HashMap<>());
    }
}
