package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.mapper.domain.CidadeDomainMapper;
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

    @Mock
    private CidadeDomainMapper cidadeDomainMapper;

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
    }

    @Test
    @DisplayName("Deve buscar lista de cidades")
    @Order(1)
    void deveBuscarListaDeCidades(){
        List<Cidade> cidades = gerarCidades();
        when(cidadeRepository.buscarCidades()).thenReturn(cidades);
        when(cidadeDomainMapper.toCidadesDomains(cidades)).thenReturn(gerarCidadesDomains());

        List<CidadeDomain> cidadesDomains = cidadeService.buscarCidades();

        assertFalse(cidadesDomains.isEmpty());
        assertEquals("Manaus", cidadesDomains.get(0).getNome());
        assertEquals("Campinas", cidadesDomains.get(1).getNome());
        assertEquals("Belo Horizonte", cidadesDomains.get(2).getNome());
        verify(cidadeRepository, times(1)).buscarCidades();
    }

    @Test
    @DisplayName("Deve buscar cidade por id quando existir")
    @Order(2)
    void deveBuscarCidadePorId(){
        Cidade cidade = gerarCidade();
        when(cidadeRepository.findById(idCidadeExistente)).thenReturn(Optional.of(cidade));
        when(cidadeDomainMapper.toCidadeDomain(cidade)).thenReturn(gerarCidadeDomain());

        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorId(idCidadeExistente);

        assertNotNull(cidadeDomain);
        verify(cidadeRepository, times(1)).findById(idCidadeExistente);
    }

    @Test
    @DisplayName("Nao deve buscar cidade por id quando não existir")
    @Order(3)
    void naoDeveBuscarCidadePorId(){
        when(cidadeRepository.findById(idCidadeInexistente)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> cidadeService.buscarCidadePorId(idCidadeInexistente));
        verify(cidadeRepository, times(1)).findById(idCidadeInexistente);
    }

    @Test
    @DisplayName("Deve buscar cidade por código quando existir")
    @Order(4)
    void deveBuscarCidadePorCodigo(){
        Cidade cidade = gerarCidade();
        when(cidadeRepository.buscarCidadePorCodigo(codigoExistente)).thenReturn(Optional.of(cidade));
        when(cidadeDomainMapper.toCidadeDomain(cidade)).thenReturn(gerarCidadeDomain());

        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorCodigo(codigoExistente);

        assertNotNull(cidadeDomain);
        verify(cidadeRepository, times(1)).buscarCidadePorCodigo(codigoExistente);
    }

    @Test
    @DisplayName("Nao deve buscar cidade por código quando não existir")
    @Order(5)
    void naoDeveBuscarCidadePorCodigo(){
        when(cidadeRepository.buscarCidadePorCodigo(codigoInexistente)).thenReturn(Optional.empty());

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
