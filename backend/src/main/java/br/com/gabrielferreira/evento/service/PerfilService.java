package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.PerfilDomain;
import br.com.gabrielferreira.evento.entity.Perfil;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.PerfilRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public List<PerfilDomain> buscarPerfis(){
        List<Perfil> perfis = perfilRepository.buscarPerfil();
        return toPerfisDomains(perfis);
    }

    public PerfilDomain buscarPerfilPorDescricao(String descricao){
        if(StringUtils.isBlank(descricao)){
            throw new MsgException("É necessário informar a descrição");
        }

        Perfil perfil = perfilRepository.buscarPerfilPorDescricao(descricao)
                .orElseThrow(() -> new NaoEncontradoException("Perfil não encontrado"));
        return toPerfilDomain(perfil);
    }

    public List<PerfilDomain> buscarPerfisPorUsuario(Long idUsuario){
        List<Perfil> perfis = perfilRepository.buscarPerfisPorUsuarioId(idUsuario);
        return toPerfisDomains(perfis);
    }
}
