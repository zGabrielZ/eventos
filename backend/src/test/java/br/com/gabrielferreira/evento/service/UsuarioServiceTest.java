package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.entity.Usuario;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.UsuarioRepository;
import br.com.gabrielferreira.evento.service.validation.UsuarioValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static br.com.gabrielferreira.evento.tests.UsuarioFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {

    @InjectMocks
    protected UsuarioService usuarioService;

    @Mock
    protected UsuarioValidator usuarioValidator;

    @Mock
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    protected ConsultaAvancadaService consultaAvancadaService;

    @Mock
    protected UsuarioRepository usuarioRepository;

    private UsuarioDomain usuarioDomainInsert;

    private Usuario usuarioInsert;

    private Long idExistente;

    private Long idInexistente;

    private UsuarioDomain usuarioDomainUpdate;

    private Usuario usuarioUpdate;

    @BeforeEach
    void setUp(){
        idExistente = 1L;
        idInexistente = -1L;

        usuarioDomainInsert = criarUsuarioDomainInsert(criarUsuarioInsertDto());
        usuarioDomainUpdate = criarUsuarioDomainUpdate(idExistente, criarUsuarioUpdateDto());

        usuarioInsert = criarUsuarioInsert(usuarioDomainInsert);
        usuarioUpdate = criarUsuarioUpdate(usuarioDomainUpdate);
    }

    @Test
    @DisplayName("Deve cadastrar usuário quando informar corretamente")
    @Order(1)
    void deveCadastrarUsuario(){
        doNothing().when(usuarioValidator).validarCampo(usuarioDomainInsert);
        doNothing().when(usuarioValidator).validarSenha(usuarioDomainInsert.getSenha());
        doNothing().when(usuarioValidator).validarEmail(usuarioDomainInsert);
        doNothing().when(usuarioValidator).validarPerfil(usuarioDomainInsert);
        when(bCryptPasswordEncoder.encode(usuarioDomainInsert.getSenha())).thenReturn(usuarioDomainInsert.getSenha());
        when(usuarioRepository.save(any())).thenReturn(usuarioInsert);

        UsuarioDomain usuarioDomain = usuarioService.cadastrarUsuario(usuarioDomainInsert);

        assertNotNull(usuarioDomain);
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve buscar usuário quando informar o id")
    @Order(2)
    void deveBuscarUsuario(){
        when(usuarioRepository.buscarPorId(idExistente)).thenReturn(Optional.of(usuarioInsert));

        UsuarioDomain usuarioDomain = usuarioService.buscarUsuarioPorId(idExistente);

        assertNotNull(usuarioDomain);
        verify(usuarioRepository, times(1)).buscarPorId(idExistente);
    }

    @Test
    @DisplayName("Não deve buscar usuário quando informar o id")
    @Order(3)
    void naoDeveBuscarUsuario(){
        when(usuarioRepository.buscarPorId(idInexistente)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> usuarioService.buscarUsuarioPorId(idInexistente));
        verify(usuarioRepository, times(1)).buscarPorId(idInexistente);
    }

    @Test
    @DisplayName("Deve atualizar usuário quando informar corretamente e o seu id")
    @Order(4)
    void deveAtualizarUsuario(){
        doNothing().when(usuarioValidator).validarCampo(usuarioDomainUpdate);
        doNothing().when(usuarioValidator).validarEmail(usuarioDomainUpdate);
        doNothing().when(usuarioValidator).validarPerfil(usuarioDomainUpdate);
        when(usuarioRepository.buscarPorId(idExistente)).thenReturn(Optional.of(usuarioInsert));
        when(usuarioRepository.save(any())).thenReturn(usuarioUpdate);

        UsuarioDomain usuarioDomain = usuarioService.atualizarUsuario(usuarioDomainUpdate);

        assertNotNull(usuarioDomain);
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve deletar usuário quando informar id existente")
    @Order(5)
    void deveDeletarUsuario() {
        when(usuarioRepository.buscarPorId(idExistente)).thenReturn(Optional.of(usuarioInsert));
        doNothing().when(usuarioRepository).deleteById(usuarioInsert.getId());

        assertDoesNotThrow(() -> usuarioService.deletarUsuarioPorId(idExistente));
        verify(usuarioRepository, times(1)).deleteById(any());
    }
}
