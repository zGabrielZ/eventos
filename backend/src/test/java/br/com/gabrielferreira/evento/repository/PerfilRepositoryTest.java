package br.com.gabrielferreira.evento.repository;

import br.com.gabrielferreira.evento.entity.Perfil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PerfilRepositoryTest {

    @Autowired
    protected PerfilRepository perfilRepository;

    @Test
    @DisplayName("Deve buscar perfis com ordem alfabetica")
    @Order(1)
    void deveBuscarPerfis(){
        List<Perfil> perfis = perfilRepository.buscarPerfil();
        assertFalse(perfis.isEmpty());
    }

    @Test
    @DisplayName("Deve buscar perfil por descrição")
    @Order(2)
    void deveBuscarPerfilPorDescricao(){
        Perfil perfil = perfilRepository.buscarPerfilPorDescricao("ROLE_ADMIN")
                .orElse(null);
        assertNotNull(perfil);
    }

    @Test
    @DisplayName("Não deve buscar perfil quando a descrição informada não estiver cadastrada")
    @Order(3)
    void naoDeveBuscarPerfilPorDescricao(){
        Perfil perfil = perfilRepository.buscarPerfilPorDescricao("ROLE")
                .orElse(null);
        assertNull(perfil);
    }

    @Test
    @DisplayName("Deve buscar perfis por id do usuário")
    @Order(4)
    void deveBuscarPerfisUsuario(){
        List<Perfil> perfis = perfilRepository.buscarPerfisPorUsuarioId(1L);
        assertFalse(perfis.isEmpty());
    }
}
