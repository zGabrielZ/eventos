package br.com.gabrielferreira.eventos.domain.service;

import br.com.gabrielferreira.eventos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.eventos.domain.model.Perfil;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.repository.UsuarioRepository;
import br.com.gabrielferreira.eventos.domain.service.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public Usuario atualizarUsuarioPorId(Long id, Usuario usuario){
        Usuario usuarioEncontrado = buscarUsuarioPorId(id);

        usuarioValidator.validarCampos(usuario);
        usuarioValidator.validarSenha(usuario.getSenha());
        usuarioValidator.validarEmail(usuarioEncontrado.getId(), usuario.getEmail());
        usuarioValidator.validarPerfil(usuario.getPerfis());

        preencherCamposUsuario(usuarioEncontrado, usuario);

        usuarioEncontrado = usuarioRepository.save(usuarioEncontrado);
        return usuarioEncontrado;
    }

    @Transactional
    public void deletarUsuarioPorId(Long id){
        Usuario usuarioEncontrado = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuarioEncontrado);
    }

    private void preencherCamposUsuario(Usuario usuarioEncontrado, Usuario usuario){
        usuarioEncontrado.setNome(usuario.getNome());
        usuarioEncontrado.setEmail(usuario.getEmail());
        usuarioEncontrado.setSenha(usuario.getSenha());

        removerPerfisNaoExistentes(usuarioEncontrado.getPerfis(), usuario.getPerfis());
        incluirNovosPerfis(usuarioEncontrado.getPerfis(), usuario.getPerfis());
    }

    private void removerPerfisNaoExistentes(List<Perfil> perfisExistentes, List<Perfil> novosPerfis){
        perfisExistentes.removeIf(perfisExistente -> perfisExistente.isNaoContemPerfil(novosPerfis));
    }

    private void incluirNovosPerfis(List<Perfil> perfisExistentes, List<Perfil> novosPerfis){
        novosPerfis.forEach(novoPerfil -> {
            if(novoPerfil.isNaoContemPerfil(perfisExistentes)){
                perfisExistentes.add(novoPerfil);
            }
        });
    }
}
