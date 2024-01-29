package br.com.gabrielferreira.eventos.domain.service;

import br.com.gabrielferreira.eventos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.eventos.domain.model.Perfil;
import br.com.gabrielferreira.eventos.domain.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public List<Perfil> buscarPerfis(){
        return perfilRepository.buscarPerfis();
    }

    public Perfil buscarPerfilPorId(Long id){
        return perfilRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Perfil não encontrado"));
    }
}
