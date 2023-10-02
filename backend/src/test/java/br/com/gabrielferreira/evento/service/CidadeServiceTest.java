package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.factory.domain.CidadeDomainFactory;
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
    private CidadeDomainFactory cidadeDomainFactory;

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

        cidadeService = new CidadeService(cidadeRepository, cidadeDomainFactory);

        List<Cidade> cidades = gerarCidades();
        List<CidadeDomain> cidadeDomains = gerarCidadesDomains();
        when(cidadeRepository.buscarCidades()).thenReturn(cidades);
        when(cidadeDomainFactory.toCidadesDomains(cidades)).thenReturn(cidadeDomains);

        Optional<Cidade> cidadeOptional = Optional.of(gerarCidade());
        CidadeDomain cidadeDomain = gerarCidadeDomain();
        when(cidadeRepository.findById(idCidadeExistente)).thenReturn(cidadeOptional);
        when(cidadeDomainFactory.toCidadeDomain(cidadeOptional.get())).thenReturn(cidadeDomain);

        when(cidadeRepository.findById(idCidadeInexistente)).thenReturn(Optional.empty());

        when(cidadeRepository.buscarCidadePorCodigo(codigoExistente)).thenReturn(cidadeOptional);

        when(cidadeRepository.buscarCidadePorCodigo(codigoInexistente)).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("Deve buscar lista de cidades")
    @Order(1)
    void deveBuscarListaDeCidades(){
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
        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorId(idCidadeExistente);

        assertNotNull(cidadeDomain);
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
        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorCodigo(codigoExistente);

        assertNotNull(cidadeDomain);
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
