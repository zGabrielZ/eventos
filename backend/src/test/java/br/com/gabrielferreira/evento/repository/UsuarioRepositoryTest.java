package br.com.gabrielferreira.evento.repository;

import br.com.gabrielferreira.evento.entity.Usuario;
import br.com.gabrielferreira.evento.repository.projection.UsuarioProjection;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioRepositoryTest {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve existir email do usuário quando informar")
    @Order(1)
    void deveExistirEmailUsuario(){
        UsuarioProjection usuario = usuarioRepository.existeEmailUsuario("teste@email.com")
                .orElse(null);
        assertNotNull(usuario);
    }

    @Test
    @DisplayName("Deve buscar usuário quando informar o id")
    @Order(2)
    void deveBuscarUsuario(){
        Usuario usuario = usuarioRepository.buscarPorId(1L)
                .orElse(null);
        assertNotNull(usuario);
    }


}
