package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.CidadeRepository;
import br.com.gabrielferreira.evento.service.validation.CidadeValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static br.com.gabrielferreira.evento.tests.CidadeFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CidadeServiceTest {

    @InjectMocks
    private CidadeService cidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private CidadeValidator cidadeValidator;

    private Long idCidadeExistente;

    private Long idCidadeInexistente;

    private String codigoExistente;

    private String codigoInexistente;

    private CidadeDomain cidadeDomainInsert;

    private CidadeDomain cidadeDomainUpdate;

    private Cidade cidadeInsert;

    private Cidade cidadeUpdate;

    @BeforeEach
    void setUp(){
        idCidadeExistente = 1L;
        idCidadeInexistente = -1L;
        codigoExistente = "MANAUS";
        codigoInexistente = "teste123";

        cidadeDomainInsert = criarCidadeDomainInsert(criarCidadeInsertDto());
        cidadeDomainUpdate = criarCidadeDomainUpdate(idCidadeExistente, criarCidadeUpdateDto());

        cidadeInsert = criarCidadeInsert(cidadeDomainInsert);
        cidadeUpdate = criarCidadeUpdate(cidadeDomainUpdate);
    }

    @Test
    @DisplayName("Deve buscar lista de cidades")
    @Order(1)
    void deveBuscarListaDeCidades(){
        List<Cidade> cidades = gerarCidades();
        when(cidadeRepository.buscarCidades()).thenReturn(cidades);

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

    @Test
    @DisplayName("Deve cadastrar cidade quando informar corretamente")
    @Order(7)
    void deveCadastrarCidade(){
        doNothing().when(cidadeValidator).validarCampos(cidadeDomainInsert);
        doNothing().when(cidadeValidator).validarNome(cidadeDomainInsert);
        doNothing().when(cidadeValidator).validarCodigo(cidadeDomainInsert);
        when(cidadeRepository.save(any())).thenReturn(cidadeInsert);

        CidadeDomain cidadeDomain = cidadeService.cadastrarCidade(cidadeDomainInsert);

        assertNotNull(cidadeDomain);
        verify(cidadeRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve atualizar cidade quando informar valores corretos")
    @Order(8)
    void deveAtualizarCidade() {
        doNothing().when(cidadeValidator).validarCampos(cidadeDomainUpdate);
        doNothing().when(cidadeValidator).validarNome(cidadeDomainUpdate);
        doNothing().when(cidadeValidator).validarCodigo(cidadeDomainUpdate);
        when(cidadeRepository.findById(idCidadeExistente)).thenReturn(Optional.of(cidadeInsert));
        when(cidadeRepository.save(any())).thenReturn(cidadeUpdate);

        CidadeDomain cidadeDomain = cidadeService.atualizarCidade(cidadeDomainUpdate);

        assertNotNull(cidadeDomain);
        verify(cidadeRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve deletar cidade quando informar id existente")
    @Order(9)
    void deveDeletarCidade() {
        when(cidadeRepository.findById(idCidadeExistente)).thenReturn(Optional.of(cidadeInsert));
        doNothing().when(cidadeRepository).deleteById(cidadeInsert.getId());

        assertDoesNotThrow(() -> cidadeService.deletarCidadePorId(idCidadeExistente));
        verify(cidadeRepository, times(1)).deleteById(any());
    }
}
