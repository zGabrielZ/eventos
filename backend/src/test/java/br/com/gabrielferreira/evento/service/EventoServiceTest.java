package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.mapper.domain.CidadeDomainMapper;
import br.com.gabrielferreira.evento.mapper.domain.EventoDomainMapper;
import br.com.gabrielferreira.evento.mapper.entity.EventoMapper;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static br.com.gabrielferreira.evento.tests.Factory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventoServiceTest {

    @InjectMocks
    private EventoService eventoService;

    @Mock
    private CidadeService cidadeService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private EventoMapper eventoMapper;

    @Mock
    private EventoDomainMapper eventoDomainMapper;

    private EventoDomain eventoInsertDomain;

    private Evento eventoInsert;

    private Evento eventoUpdate;

    private Long idEventoExistente;

    private Long idEventoInexistente;

    private EventoDomain eventoUpdateDomain;

    @BeforeEach
    void setUp(){
        idEventoExistente = 1L;
        idEventoInexistente = -1L;
        eventoInsertDomain = EventoDomainMapper.INSTANCE.toEventoDomain(criarEventoInsertDto());
        eventoUpdateDomain = EventoDomainMapper.INSTANCE.toEventoDomain(criarEventoUpdateDto());
        eventoUpdateDomain.setId(idEventoExistente);

        eventoInsert = gerarEventoInsert();
        eventoUpdate = gerarEventoUpdate();
    }

    @Test
    @DisplayName("Deve cadastrar evento quando informar valores corretos")
    @Order(1)
    void deveCadastrarEvento(){
        when(cidadeService.buscarCidadePorId(eventoInsertDomain.getCidade().getId())).thenReturn(CidadeDomainMapper.INSTANCE.toCidadeDomain(gerarCidade()));
        when(eventoMapper.toEvento(eventoInsertDomain)).thenReturn(eventoInsert);
        when(eventoRepository.save(any())).thenReturn(eventoInsert);
        when(eventoDomainMapper.toEventoDomain(eventoInsert)).thenReturn(eventoInsertDomain);

        EventoDomain eventoDomain = eventoService.cadastrarEvento(eventoInsertDomain);

        assertNotNull(eventoDomain);
        verify(eventoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve buscar evento por id quando existir")
    @Order(2)
    void deveBuscarEventoPorId() {
        when(eventoRepository.buscarEventoPorId(idEventoExistente)).thenReturn(Optional.of(eventoInsert));
        when(eventoDomainMapper.toEventoDomain(eventoInsert)).thenReturn(eventoInsertDomain);

        EventoDomain eventoDomain = eventoService.buscarEventoPorId(idEventoExistente);

        assertNotNull(eventoDomain);
        verify(eventoRepository, times(1)).buscarEventoPorId(idEventoExistente);
    }

    @Test
    @DisplayName("Nao deve buscar evento por id quando não existir")
    @Order(3)
    void naoDeveBuscarEventoPorId() {
        when(eventoRepository.buscarEventoPorId(idEventoInexistente)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> eventoService.buscarEventoPorId(idEventoInexistente));
        verify(eventoRepository, times(1)).buscarEventoPorId(idEventoInexistente);
    }

    @Test
    @DisplayName("Deve atualizar evento quando informar valores corretos")
    @Order(4)
    void deveAtualizarEventos() {
        when(eventoRepository.buscarEventoPorId(idEventoExistente)).thenReturn(Optional.of(eventoInsert));
        when(cidadeService.buscarCidadePorId(eventoUpdateDomain.getCidade().getId())).thenReturn(CidadeDomainMapper.INSTANCE.toCidadeDomain(gerarCidade2()));
        doNothing().when(eventoDomainMapper).updateEventoDomain(any(), any());
        when(eventoMapper.toEvento(eventoUpdateDomain)).thenReturn(eventoUpdate);
        when(eventoRepository.save(any())).thenReturn(eventoUpdate);
        when(eventoDomainMapper.toEventoDomain(eventoUpdate)).thenReturn(eventoUpdateDomain);

        EventoDomain eventoDomain = eventoService.atualizarEvento(eventoUpdateDomain);

        assertNotNull(eventoDomain);
        verify(eventoRepository, times(1)).save(any());
    }


    @Test
    @DisplayName("Deve deletar evento quando informar id existente")
    @Order(6)
    void deveDeletarEvento() {
        when(eventoRepository.buscarEventoPorId(idEventoExistente)).thenReturn(Optional.of(eventoInsert));
        when(eventoDomainMapper.toEventoDomain(eventoInsert)).thenReturn(eventoInsertDomain);
        doNothing().when(eventoRepository).deleteById(eventoInsert.getId());

        assertDoesNotThrow(() -> eventoService.deletarEventoPorId(idEventoExistente));
        verify(eventoRepository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Não deve deletar evento quando informar id inexistente")
    @Order(7)
    void naoDeveDeletarEvento() {
        when(eventoRepository.buscarEventoPorId(idEventoInexistente)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> eventoService.deletarEventoPorId(idEventoInexistente));
        verify(eventoRepository, never()).deleteById(any());
    }
}
