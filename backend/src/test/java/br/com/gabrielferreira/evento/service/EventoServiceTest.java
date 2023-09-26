package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dto.EventoDTO;
import br.com.gabrielferreira.evento.dto.EventoFiltroDTO;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static br.com.gabrielferreira.evento.tests.Factory.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventoServiceTest {

    @InjectMocks
    private EventoService eventoService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private CidadeService cidadeService;

    @Mock
    private ConsultaAvancadaService consultaAvancadaService;

    private Long idEventoExistente;

    private Long idEventoInexistente;

    private EventoFiltroDTO filtroDTO;

    @BeforeEach
    void setUp(){
        idEventoExistente = 1L;
        idEventoInexistente = -1L;

        when(eventoRepository.buscarEventoPorId(idEventoExistente)).thenReturn(Optional.of(gerarEvento()));
        when(eventoRepository.buscarEventoPorId(idEventoInexistente)).thenReturn(Optional.empty());

        when(eventoRepository.buscarEventos(any())).thenReturn(gerarPageEventos());
    }

    @Test
    @DisplayName("Deve buscar evento por id quando existir")
    @Order(1)
    void deveBuscarEventoPorId(){
        EventoDTO eventoDTO = eventoService.buscarEventoPorId(idEventoExistente);

        assertNotNull(eventoDTO);
        verify(eventoRepository, times(1)).buscarEventoPorId(idEventoExistente);
    }

    @Test
    @DisplayName("Nao deve buscar evento por id quando não existir")
    @Order(2)
    void naoDeveBuscarEventoPorId(){
        assertThrows(NaoEncontradoException.class, () -> eventoService.buscarEventoPorId(idEventoInexistente));
        verify(eventoRepository, times(1)).buscarEventoPorId(idEventoInexistente);
    }

    @Test
    @DisplayName("Deve buscar eventos paginados quando existir")
    @Order(3)
    void deveRetornarEventosPaginados(){
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id"));
        Page<EventoDTO> eventoDTOS = eventoService.buscarEventos(pageRequest);

        assertFalse(eventoDTOS.isEmpty());
        assertNotNull(eventoDTOS);
        verify(eventoRepository, times(1)).buscarEventos(pageRequest);
    }
}
