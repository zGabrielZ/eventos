package br.com.gabrielferreira.evento.repository;

import br.com.gabrielferreira.evento.entities.Evento;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventoRepositoryTest {

    @Autowired
    protected EventoRepository eventoRepository;

    @Test
    @DisplayName("Deve buscar lista de eventos")
    @Order(1)
    void deveBuscarListaDeEventos(){
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Evento> eventos = eventoRepository.buscarEventos(pageRequest);

        assertFalse(eventos.isEmpty());
    }

    @Test
    @DisplayName("Deve buscar evento por id quando informar")
    @Order(2)
    void deveBuscarEventoPorId(){
        Optional<Evento> evento = eventoRepository.buscarEventoPorId(1L);

        assertTrue(evento.isPresent());
    }

    @Test
    @DisplayName("Não deve buscar evento por id quando informar id inválido")
    @Order(3)
    void naoDeveBuscarEventoPorId(){
        Optional<Evento> evento = eventoRepository.buscarEventoPorId(-1L);

        assertFalse(evento.isPresent());
    }
}
