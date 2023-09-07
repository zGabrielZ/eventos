package br.com.gabrielferreira.evento.repository;

import br.com.gabrielferreira.evento.entities.Cidade;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CidadeRepositoryTest {

    @Autowired
    protected CidadeRepository cidadeRepository;

    @Test
    @DisplayName("Deve buscar lista de cidades")
    @Order(1)
    void deveBuscarListaDeCidades(){
        List<Cidade> cidades = cidadeRepository.buscarCidades();

        assertFalse(cidades.isEmpty());
    }

    @Test
    @DisplayName("Deve buscar cidade por codigo quando informar")
    @Order(2)
    void deveBuscarCidadePorCodigo(){
        Optional<Cidade> cidade = cidadeRepository.buscarCidadePorCodigo("SAO_PAULO");

        assertTrue(cidade.isPresent());
    }

    @Test
    @DisplayName("Não deve buscar cidade por codigo quando informar código inválido")
    @Order(3)
    void naoDeveBuscarCidadePorCodigo(){
        Optional<Cidade> cidade = cidadeRepository.buscarCidadePorCodigo("teste123");

        assertFalse(cidade.isPresent());
    }
}
