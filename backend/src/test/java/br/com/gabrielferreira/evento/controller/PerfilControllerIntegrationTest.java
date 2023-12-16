package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.utils.TokenUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PerfilControllerIntegrationTest {

    private static final String URL = "/perfis";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected TokenUtils tokenUtils;

    private Long idPerfilExistente;

    private Long idPerfilInexistente;

    private String descricaoExistente;

    private String descricaoInexistente;

    private Long idUsuarioExistente;

    private String tokenAdmin;

    private String tokenClient;

    @BeforeEach
    void setUp(){
        idPerfilExistente = 1L;
        idPerfilInexistente = -1L;
        descricaoExistente = "ROLE_ADMIN";
        descricaoInexistente = "ROLE";
        idUsuarioExistente = 1L;

        tokenAdmin = tokenUtils.gerarToken(mockMvc, "teste@email.com", "123");
        tokenClient = tokenUtils.gerarToken(mockMvc, "gabriel123@email.com", "123");
    }

    @Test
    @DisplayName("Deve buscar perfil quando existir")
    @Order(1)
    void deveBuscarPerfil() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idPerfilExistente)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.descricao").exists());
        resultActions.andExpect(jsonPath("$.tipo").exists());
    }

    @Test
    @DisplayName("Não deve buscar perfil quando não existir")
    @Order(2)
    void naoDeveBuscarPerfil() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idPerfilInexistente)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Perfil não encontrado"));
    }

    @Test
    @DisplayName("Deve buscar lista de perfis")
    @Order(3)
    void deveBuscarListaDePerfis() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve buscar perfil quando informar a descrição existente")
    @Order(4)
    void deveBuscarPerfilViaDescricao() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/buscar?descricao=".concat(descricaoExistente)))
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.descricao").exists());
        resultActions.andExpect(jsonPath("$.tipo").exists());
    }

    @Test
    @DisplayName("Não deve buscar perfil quando informar a descrição inexistente")
    @Order(5)
    void naoDeveBuscarPerfilViaDescricao() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/buscar?descricao=".concat(descricaoInexistente)))
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Perfil não encontrado"));
    }

    @Test
    @DisplayName("Não deve buscar perfil quando não informar a descrição")
    @Order(6)
    void naoDeveBuscarPerfilNaoInformarDescricao() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/buscar?descricao="))
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.mensagem").value("É necessário informar a descrição"));
    }

    @Test
    @DisplayName("Deve buscar lista de perfis quando informar o id do usuário")
    @Order(7)
    void deveBuscarListaDePerfisPorUsuario() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/usuarios/{idUsuario}"), idUsuarioExistente)
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Não deve buscar lista de perfis quando informar for client")
    @Order(8)
    void naoDeveBuscarListaDePerfisPorUsuarioQuandoForClient() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/usuarios/{idUsuario}"), idUsuarioExistente)
                        .header("Authorization", "Bearer " + tokenClient)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isForbidden());
        resultActions.andExpect(jsonPath("$.erro").value("Proibido"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Você não tem a permissão de realizar esta ação"));
    }
}
