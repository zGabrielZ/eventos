package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.PerfilDomain;
import br.com.gabrielferreira.evento.entity.Perfil;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.gabrielferreira.evento.factory.domain.PerfilDomainFactory.*;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilDomain buscarPerfilPorId(Long id){
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Perfil não encontrado"));
        return toPerfilDomain(perfil);
    }
}
