package br.com.gabrielferreira.evento.tests;

import br.com.gabrielferreira.evento.domain.PerfilDomain;
import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.dto.request.PerfilIdResquestDTO;
import br.com.gabrielferreira.evento.dto.request.UsuarioResquestDTO;
import br.com.gabrielferreira.evento.dto.request.UsuarioUpdateResquestDTO;
import br.com.gabrielferreira.evento.entity.Perfil;
import br.com.gabrielferreira.evento.entity.Usuario;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.evento.utils.DataUtils.UTC;

public class UsuarioFactory {

    private UsuarioFactory(){}

    public static UsuarioResquestDTO criarUsuarioInsertDto(){
        return UsuarioResquestDTO.builder()
                .nome("Teste usuário")
                .email("teste@email.com.br")
                .senha("Teste123@_")
                .perfis(criarPerfisDto())
                .build();
    }

    public static UsuarioDomain criarUsuarioDomainInsert(UsuarioResquestDTO usuarioResquestDTO){
        return UsuarioDomain.builder()
                .nome(usuarioResquestDTO.getNome())
                .email(usuarioResquestDTO.getEmail())
                .senha(usuarioResquestDTO.getSenha())
                .perfis(criarPerfisDomains(usuarioResquestDTO.getPerfis()))
                .build();
    }

    public static Usuario criarUsuarioInsert(UsuarioDomain usuarioDomain){
        return Usuario.builder()
                .id(usuarioDomain.getId())
                .nome(usuarioDomain.getNome())
                .email(usuarioDomain.getEmail())
                .senha(usuarioDomain.getSenha())
                .perfis(criarPerfis(usuarioDomain.getPerfis()))
                .createdAt(ZonedDateTime.now(UTC))
                .build();
    }

    private static List<PerfilIdResquestDTO> criarPerfisDto(){
        List<PerfilIdResquestDTO> perfis = new ArrayList<>();
        perfis.add(PerfilIdResquestDTO.builder().id(1L).build());
        perfis.add(PerfilIdResquestDTO.builder().id(2L).build());
        return perfis;
    }

    private static List<PerfilDomain> criarPerfisDomains(List<PerfilIdResquestDTO> perfis){
        List<PerfilDomain> perfilDomains = new ArrayList<>();
        perfis.forEach(perfil -> perfilDomains.add(PerfilDomain.builder().id(perfil.getId()).build()));
        return perfilDomains;
    }

    private static List<Perfil> criarPerfis(List<PerfilDomain> perfilDomains){
        List<Perfil> perfis = new ArrayList<>();
        perfilDomains.forEach(perfil -> perfis.add(Perfil.builder().id(perfil.getId()).build()));
        return perfis;
    }

    public static UsuarioUpdateResquestDTO criarUsuarioUpdateDto(){
        return UsuarioUpdateResquestDTO.builder()
                .nome("Teste usuário 2")
                .email("teste123@email.com")
                .perfis(criarPerfisDto())
                .build();
    }

    public static UsuarioDomain criarUsuarioDomainUpdate(Long id, UsuarioUpdateResquestDTO usuarioUpdateResquestDTO){
        return UsuarioDomain.builder()
                .id(id)
                .nome(usuarioUpdateResquestDTO.getNome())
                .email(usuarioUpdateResquestDTO.getEmail())
                .perfis(criarPerfisDomains(usuarioUpdateResquestDTO.getPerfis()))
                .build();
    }

    public static Usuario criarUsuarioUpdate(UsuarioDomain usuarioDomain){
        return Usuario.builder()
                .id(usuarioDomain.getId())
                .nome(usuarioDomain.getNome())
                .email(usuarioDomain.getEmail())
                .perfis(criarPerfis(usuarioDomain.getPerfis()))
                .updatedAt(ZonedDateTime.now(UTC))
                .build();
    }
}
