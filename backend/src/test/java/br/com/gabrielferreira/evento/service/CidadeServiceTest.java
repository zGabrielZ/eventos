package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dto.CidadeDTO;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.CidadeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static br.com.gabrielferreira.evento.tests.Factory.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CidadeServiceTest {

    @InjectMocks
    private CidadeService cidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    private Long idCidadeExistente;

    private Long idCidadeInexistente;

    private String codigoExistente;

    private String codigoInexistente;

    @BeforeEach
    void setUp(){
        idCidadeExistente = 1L;
        idCidadeInexistente = -1L;

        codigoExistente = "MANAUS";
        codigoInexistente = "teste123";

        cidadeService = new CidadeService(cidadeRepository);

        when(cidadeRepository.buscarCidades()).thenReturn(gerarCidades());

        when(cidadeRepository.findById(idCidadeExistente)).thenReturn(Optional.of(gerarCidade()));
        when(cidadeRepository.findById(idCidadeInexistente)).thenReturn(Optional.empty());

        when(cidadeRepository.buscarCidadePorCodigo(codigoExistente)).thenReturn(Optional.of(gerarCidade()));
        when(cidadeRepository.buscarCidadePorCodigo(codigoInexistente)).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("Deve buscar lista de cidades")
    @Order(1)
    void deveBuscarListaDeCidades(){
        List<CidadeDTO> cidades = cidadeService.buscarCidades();

        assertFalse(cidades.isEmpty());
        assertEquals("Manaus", cidades.get(0).nome());
        assertEquals("Campinas", cidades.get(1).nome());
        assertEquals("Belo Horizonte", cidades.get(2).nome());
        verify(cidadeRepository, times(1)).buscarCidades();
    }

    @Test
    @DisplayName("Deve buscar cidade por id quando existir")
    @Order(2)
    void deveBuscarCidadePorId(){
        CidadeDTO cidade = cidadeService.buscarCidadePorId(idCidadeExistente);

        assertNotNull(cidade);
        verify(cidadeRepository, times(1)).findById(idCidadeExistente);
    }

    @Test
    @DisplayName("Nao deve buscar cidade por id quando não existir")
    @Order(3)
    void naoDeveBuscarCidadePorId(){
        assertThrows(NaoEncontradoException.class, () -> cidadeService.buscarCidadePorId(idCidadeInexistente));
        verify(cidadeRepository, times(1)).findById(idCidadeInexistente);
    }

    @Test
    @DisplayName("Deve buscar cidade por código quando existir")
    @Order(4)
    void deveBuscarCidadePorCodigo(){
        CidadeDTO cidade = cidadeService.buscarCidadePorCodigo(codigoExistente);

        assertNotNull(cidade);
        verify(cidadeRepository, times(1)).buscarCidadePorCodigo(codigoExistente);
    }

    @Test
    @DisplayName("Nao deve buscar cidade por código quando não existir")
    @Order(5)
    void naoDeveBuscarCidadePorCodigo(){
        assertThrows(NaoEncontradoException.class, () -> cidadeService.buscarCidadePorCodigo(codigoInexistente));
        verify(cidadeRepository, times(1)).buscarCidadePorCodigo(codigoInexistente);
    }

    @Test
    @DisplayName("Nao deve buscar cidade por código quando não informar")
    @Order(6)
    void naoDeveBuscarCidadePorCodigoQuandoNaoInformar(){
        assertThrows(MsgException.class, () -> cidadeService.buscarCidadePorCodigo(null));
        verify(cidadeRepository, never()).buscarCidadePorCodigo(anyString());
    }
}
