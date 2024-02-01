package br.com.gabrielferreira.eventos.domain.service;

import br.com.gabrielferreira.eventos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.repository.UsuarioRepository;
import br.com.gabrielferreira.eventos.domain.service.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioValidator usuarioValidator;

    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario){
        usuarioValidator.validarCampos(usuario);
        usuarioValidator.validarSenha(usuario.getSenha());
        usuarioValidator.validarEmail(null, usuario.getEmail());
        usuarioValidator.validarPerfil(usuario.getPerfis());

        usuario = usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario buscarUsuarioPorId(Long id){
        return usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado"));
    }
}
