package br.com.gabrielferreira.eventos.domain.service;

import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario){
        usuario = usuarioRepository.save(usuario);
        return usuario;
    }
}
