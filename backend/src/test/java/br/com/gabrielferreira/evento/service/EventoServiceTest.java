package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.entity.Evento;
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

    private Long idEventoExistente;

    private Long idEventoInexistente;

    private EventoRequestDTO eventoRequestDTO;

    private EventoRequestDTO eventoUpdateDTO;

    @BeforeEach
    void setUp(){
        idEventoExistente = 1L;
        idEventoInexistente = -1L;
        eventoRequestDTO = criarEventoInsertDto();
        eventoUpdateDTO = criarEventoUpdateDto();

        Evento evento = gerarEvento();
        when(eventoRepository.buscarEventoPorId(idEventoExistente)).thenReturn(Optional.of(evento));
        when(eventoRepository.buscarEventoPorId(idEventoInexistente)).thenReturn(Optional.empty());

        when(eventoRepository.buscarEventos(any())).thenReturn(gerarPageEventos());

        when(eventoRepository.save(any())).thenReturn(evento);
        when(cidadeService.buscarCidade(any())).thenReturn(gerarCidade());

        doNothing().when(eventoRepository).delete(evento);
    }

    @Test
    @DisplayName("Deve buscar evento por id quando existir")
    @Order(1)
    void deveBuscarEventoPorId(){
        EventoResponseDTO eventoResponseDTO = eventoService.buscarEventoPorId(idEventoExistente);

        assertNotNull(eventoResponseDTO);
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
        Page<EventoResponseDTO> eventoDTOS = eventoService.buscarEventos(pageRequest);

        assertFalse(eventoDTOS.isEmpty());
        assertNotNull(eventoDTOS);
        verify(eventoRepository, times(1)).buscarEventos(pageRequest);
    }

    @Test
    @DisplayName("Deve cadastrar evento quando informar valores corretos")
    @Order(4)
    void deveCadastrarEventos(){
        EventoResponseDTO eventoResponseDTO = eventoService.cadastrarEvento(eventoRequestDTO);

        assertNotNull(eventoResponseDTO);
        verify(eventoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve atualizar evento quando informar valores corretos")
    @Order(5)
    void deveAtualizarEventos(){
        EventoResponseDTO eventoResponseDTO = eventoService.atualizarEvento(idEventoExistente, eventoUpdateDTO);

        assertNotNull(eventoResponseDTO);
        verify(eventoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve deletar evento quando informar id existente")
    @Order(6)
    void deveDeletarEvento(){
        assertDoesNotThrow(() -> eventoService.deletarEventoPorId(idEventoExistente));
        verify(eventoRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Não deve deletar evento quando informar id inexistente")
    @Order(7)
    void naoDeveDeletarCategoria(){
        assertThrows(NaoEncontradoException.class, () -> eventoService.deletarEventoPorId(idEventoInexistente));
        verify(eventoRepository, never()).delete(any());
    }


}
