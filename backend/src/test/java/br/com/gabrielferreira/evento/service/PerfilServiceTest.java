package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.PerfilDomain;
import br.com.gabrielferreira.evento.entity.Perfil;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.PerfilRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static br.com.gabrielferreira.evento.tests.PerfilFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PerfilServiceTest {

    @InjectMocks
    private PerfilService perfilService;

    @Mock
    private PerfilRepository perfilRepository;

    private Long idPerfilExistente;

    private Long idPerfilInexistente;

    private String descricaoExistente;

    private String descricaoInexistente;

    private Long idUsuarioExistente;

    @BeforeEach
    void setUp(){
        idPerfilExistente = 1L;
        idPerfilInexistente = -1L;
        descricaoExistente = "ROLE_ADMIN";
        descricaoInexistente = "ROLE";
        idUsuarioExistente = 1L;
    }

    @Test
    @DisplayName("Deve buscar perfil por id quando for existente")
    @Order(1)
    void deveBuscarPerfilPorId(){
        Perfil perfil = gerarPerfil();
        when(perfilRepository.findById(idPerfilExistente)).thenReturn(Optional.of(perfil));

        PerfilDomain perfilDomain = perfilService.buscarPerfilPorId(idPerfilExistente);

        assertNotNull(perfilDomain);
        verify(perfilRepository, times(1)).findById(idPerfilExistente);
    }

    @Test
    @DisplayName("Não deve buscar perfil por id quando for inexistente")
    @Order(2)
    void naoDeveBuscarPerfilPorId(){
        when(perfilRepository.findById(idPerfilInexistente)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> perfilService.buscarPerfilPorId(idPerfilExistente));
        verify(perfilRepository, times(1)).findById(idPerfilExistente);
    }

    @Test
    @DisplayName("Deve buscar lista de perfis")
    @Order(3)
    void deveBuscarListaDePerfis(){
        List<Perfil> perfis = gerarPerfis();
        when(perfilRepository.buscarPerfil()).thenReturn(perfis);

        List<PerfilDomain> perfilDomains = perfilService.buscarPerfis();

        assertFalse(perfilDomains.isEmpty());
        assertEquals("Adminstrador", perfilDomains.get(0).getTipo());
        assertEquals("Cliente", perfilDomains.get(1).getTipo());
        verify(perfilRepository, times(1)).buscarPerfil();
    }

    @Test
    @DisplayName("Deve buscar perfil por descrição quando for existente")
    @Order(4)
    void deveBuscarPerfilPorDescricao(){
        Perfil perfil = gerarPerfil();
        when(perfilRepository.buscarPerfilPorDescricao(descricaoExistente)).thenReturn(Optional.of(perfil));

        PerfilDomain perfilDomain = perfilService.buscarPerfilPorDescricao(descricaoExistente);

        assertNotNull(perfilDomain);
        verify(perfilRepository, times(1)).buscarPerfilPorDescricao(descricaoExistente);
    }

    @Test
    @DisplayName("Não deve buscar perfil por descrição quando for inexistente")
    @Order(5)
    void naoDeveBuscarPerfilPorDescricao(){
        when(perfilRepository.buscarPerfilPorDescricao(descricaoInexistente)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> perfilService.buscarPerfilPorDescricao(descricaoInexistente));
        verify(perfilRepository, times(1)).buscarPerfilPorDescricao(descricaoInexistente);
    }

    @Test
    @DisplayName("Deve buscar lista de perfis quando informar o id do usuário")
    @Order(6)
    void deveBuscarListaDePerfisUsuario(){
        List<Perfil> perfis = gerarPerfis();
        when(perfilRepository.buscarPerfisPorUsuarioId(idUsuarioExistente)).thenReturn(perfis);

        List<PerfilDomain> perfilDomains = perfilService.buscarPerfisPorUsuario(idUsuarioExistente);

        assertFalse(perfilDomains.isEmpty());
        assertEquals("Adminstrador", perfilDomains.get(0).getTipo());
        assertEquals("Cliente", perfilDomains.get(1).getTipo());
        verify(perfilRepository, times(1)).buscarPerfisPorUsuarioId(idUsuarioExistente);
    }
}
