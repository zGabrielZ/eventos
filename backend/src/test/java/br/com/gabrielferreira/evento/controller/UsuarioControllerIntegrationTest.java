package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.dto.request.PerfilIdResquestDTO;
import br.com.gabrielferreira.evento.dto.request.UsuarioResquestDTO;
import br.com.gabrielferreira.evento.dto.request.UsuarioUpdateResquestDTO;
import br.com.gabrielferreira.evento.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static br.com.gabrielferreira.evento.tests.UsuarioFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllerIntegrationTest {

    private static final String URL = "/usuarios";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;
    private static final String SENHA_COM_ESPACO = "Teste123@   ";
    private static final String SENHA_SEM_CARACTERES_ESPECIAIS = "Teste123";
    private static final String SENHA_MINUSCULAS = "teste123@";
    private static final String SENHA_MAIUSCULAS = "TESTE123@";
    private static final String SENHA_SEM_DIGITO = "TesTE@";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TokenUtils tokenUtils;

    private Long idUsuarioExistente;

    private Long idUsuarioInexistente;

    private UsuarioResquestDTO usuarioResquestDTO;

    private UsuarioUpdateResquestDTO usuarioUpdateResquestDTO;

    private String tokenAdmin;

    private String tokenClient;

    @BeforeEach
    void setUp(){
        idUsuarioExistente = 1L;
        idUsuarioInexistente = -1L;

        usuarioResquestDTO = criarUsuarioInsertDto();
        usuarioUpdateResquestDTO = criarUsuarioUpdateDto();

        tokenAdmin = tokenUtils.gerarToken(mockMvc, "teste@email.com", "123");
        tokenClient = tokenUtils.gerarToken(mockMvc, "gabriel123@email.com", "123");
    }

    @Test
    @DisplayName("Deve cadastrar um usuário quando informar valores correto")
    @Order(1)
    void deveCadastrarUsuario() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(usuarioResquestDTO);

        String nomeEsperado = usuarioResquestDTO.getNome();
        String emailEsperado = usuarioResquestDTO.getEmail();

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
        resultActions.andExpect(jsonPath("$.email").value(emailEsperado));
        resultActions.andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Não deve cadastrar um usuário quando a senha estiver com espaço")
    @Order(2)
    void naoDeveCadastrarUsuarioQuandoTiverEspacoSenha() throws Exception{
        usuarioResquestDTO.setSenha(SENHA_COM_ESPACO);
        String jsonBody = objectMapper.writeValueAsString(usuarioResquestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Não vai ser possível cadastrar este usuário pois a senha possui espaço em branco"));
    }

    @Test
    @DisplayName("Não deve cadastrar um usuário quando tiver perfil duplicado")
    @Order(3)
    void naoDeveCadastrarUsuarioQuandoTiverPerfilDuplicado() throws Exception{
        PerfilIdResquestDTO perfilIdResquestDTO = PerfilIdResquestDTO.builder().id(1L).build();
        usuarioResquestDTO.setPerfis(Arrays.asList(perfilIdResquestDTO, perfilIdResquestDTO));
        String jsonBody = objectMapper.writeValueAsString(usuarioResquestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Não vai ser possível cadastrar este usuário pois tem perfis duplicados ou mais de duplicados"));
    }

    @Test
    @DisplayName("Não deve cadastrar um usuário quando a senha não tiver caracteres especiais")
    @Order(4)
    void naoDeveCadastrarUsuarioQuandoSenhaNaoTiverCaracteresEspeciais() throws Exception{
        usuarioResquestDTO.setSenha(SENHA_SEM_CARACTERES_ESPECIAIS);
        String jsonBody = objectMapper.writeValueAsString(usuarioResquestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("A senha informada tem que ter pelo menos uma caractere especial"));
    }

    @Test
    @DisplayName("Não deve cadastrar um usuário quando a senha não tiver caracteres maiúsculas")
    @Order(5)
    void naoDeveCadastrarUsuarioQuandoSenhaNaoTiverCaracteresMaiusculas() throws Exception{
        usuarioResquestDTO.setSenha(SENHA_MINUSCULAS);
        String jsonBody = objectMapper.writeValueAsString(usuarioResquestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("A senha informada tem que ter pelo menos uma caractere maiúsculas"));
    }

    @Test
    @DisplayName("Não deve cadastrar um usuário quando a senha não tiver caracteres minúsculas")
    @Order(6)
    void naoDeveCadastrarUsuarioQuandoSenhaNaoTiverCaracteresMinusculas() throws Exception{
        usuarioResquestDTO.setSenha(SENHA_MAIUSCULAS);
        String jsonBody = objectMapper.writeValueAsString(usuarioResquestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("A senha informada tem que ter pelo menos uma caractere minúsculas"));
    }

    @Test
    @DisplayName("Não deve cadastrar um usuário quando a senha não tiver caracteres dígito")
    @Order(7)
    void naoDeveCadastrarUsuarioQuandoSenhaNaoTiverCaracteresDigito() throws Exception{
        usuarioResquestDTO.setSenha(SENHA_SEM_DIGITO);
        String jsonBody = objectMapper.writeValueAsString(usuarioResquestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("A senha informada tem que ter pelo menos um caractere dígito"));
    }

    @Test
    @DisplayName("Não deve cadastrar um usuário quando informar email já cadastrado")
    @Order(8)
    void naoDeveCadastrarUsuarioQuandoEmailJaCadastrado() throws Exception{
        usuarioResquestDTO.setEmail("teste@email.com");
        String jsonBody = objectMapper.writeValueAsString(usuarioResquestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Não vai ser possível cadastrar este usuário pois o email 'teste@email.com' já foi cadastrado"));
    }

    @Test
    @DisplayName("Não deve cadastrar um usuário quando informar perfil inexistente")
    @Order(9)
    void naoDeveCadastrarUsuarioQuandoInformarPerfilInexistente() throws Exception{
        PerfilIdResquestDTO perfilIdResquestDTO = PerfilIdResquestDTO.builder().id(-1L).build();
        usuarioResquestDTO.setPerfis(List.of(perfilIdResquestDTO));
        String jsonBody = objectMapper.writeValueAsString(usuarioResquestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.erro").value("Não encontrado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Perfil não encontrado"));
    }

    @Test
    @DisplayName("Deve buscar usuário quando informar o id")
    @Order(10)
    void deveBuscarUsuario() throws Exception{
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idUsuarioExistente)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.email").exists());
        resultActions.andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Não deve buscar usuário quando informar o id")
    @Order(11)
    void naoDeveBuscarUsuario() throws Exception{
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idUsuarioInexistente)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.erro").value("Não encontrado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }

    @Test
    @DisplayName("Deve atualizar um usuário quando informar valores correto")
    @Order(12)
    void deveAtualizarUsuario() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(usuarioUpdateResquestDTO);

        Long idEsperado = idUsuarioExistente;
        String nomeEsperado = usuarioUpdateResquestDTO.getNome();
        String emailEsperado = usuarioUpdateResquestDTO.getEmail();

        ResultActions resultActions = mockMvc
                .perform(put(URL.concat("/{id}"), idUsuarioExistente)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").value(idEsperado));
        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
        resultActions.andExpect(jsonPath("$.email").value(emailEsperado));
        resultActions.andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Não deve atualizar um usuário quando informar email já cadastrado")
    @Order(13)
    void naoDeveAtualizarUsuarioQuandoInformarEmailJaCadastrado() throws Exception{
        usuarioUpdateResquestDTO.setEmail("gabriel123@email.com");
        String jsonBody = objectMapper.writeValueAsString(usuarioUpdateResquestDTO);

        ResultActions resultActions = mockMvc
                .perform(put(URL.concat("/{id}"), idUsuarioExistente)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Não vai ser possível atualizar este usuário pois o email 'gabriel123@email.com' já foi cadastrado"));
    }

    @Test
    @DisplayName("Deve deletar usuário quando informar o id")
    @Order(14)
    void deveDeletarUsuario() throws Exception{
        ResultActions resultActions = mockMvc
                .perform(delete(URL.concat("/{id}"), idUsuarioExistente)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve buscar usuários paginados quando existir")
    @Order(15)
    void deveBuscarUsuariosPaginados() throws Exception {
        String filtro = "?page=0&size=5&sort=id,desc&id=4&nome=Teste&data=2021-05-03" +
                "&idsPerfis=1,2";

        ResultActions resultActions = mockMvc
                .perform(get(URL.concat(filtro))
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }

    @Test
    @DisplayName("Não deve buscar usuários paginados quando for client")
    @Order(16)
    void naoDeveBuscarUsuariosPaginadosQuandoForClient() throws Exception {
        String filtro = "?page=0&size=5&sort=id,desc&id=4&nome=Teste&data=2021-05-03" +
                "&idsPerfis=1,2";

        ResultActions resultActions = mockMvc
                .perform(get(URL.concat(filtro))
                        .header("Authorization", "Bearer " + tokenClient)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isForbidden());
        resultActions.andExpect(jsonPath("$.erro").value("Proibido"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Você não tem a permissão de realizar esta ação"));
    }
}
