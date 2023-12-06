package br.com.gabrielferreira.evento.factory.domain;

import br.com.gabrielferreira.evento.domain.PerfilDomain;
import br.com.gabrielferreira.evento.dto.request.PerfilIdResquestDTO;
import br.com.gabrielferreira.evento.entity.Perfil;

import java.util.ArrayList;
import java.util.List;

public class PerfilDomainFactory {

    private PerfilDomainFactory(){}

    public static PerfilDomain toCreatePerfilDomain(PerfilIdResquestDTO perfilIdResquestDTO){
        if(perfilIdResquestDTO != null){
            return PerfilDomain.builder()
                    .id(perfilIdResquestDTO.getId())
                    .build();
        }
        return null;
    }

    public static List<PerfilDomain> toCreatePerfisDomains(List<PerfilIdResquestDTO> perfilIdResquestDTOS){
        List<PerfilDomain> perfis = new ArrayList<>();
        perfilIdResquestDTOS.forEach(perfilIdResquestDTO -> {
            PerfilDomain perfilDomain = toCreatePerfilDomain(perfilIdResquestDTO);
            perfis.add(perfilDomain);
        });
        return perfis;
    }

    public static PerfilDomain toPerfilDomain(Perfil perfil){
        if(perfil != null){
            return PerfilDomain.builder()
                    .id(perfil.getId())
                    .descricao(perfil.getDescricao())
                    .tipo(perfil.getTipo())
                    .build();
        }
        return null;
    }

    public static List<PerfilDomain> toPerfisDomains(List<Perfil> perfis){
        return perfis.stream().map(PerfilDomainFactory::toPerfilDomain).toList();
    }
}
