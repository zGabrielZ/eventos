package br.com.gabrielferreira.evento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    protected ObjectMapper objectMapper;

    private Long idPerfilExistente;

    private Long idPerfilInexistente;

    private String descricaoExistente;

    private String descricaoInexistente;

    private Long idUsuarioExistente;

    @BeforeEach
    void setUp(){
        idPerfilExistente = 1L;
        idPerfilInexistente = -1L;
        descricaoExistente = "ROLE_ADMIN";
        descricaoInexistente = "ROLE";
        idUsuarioExistente = 1L;
    }

    @Test
    @DisplayName("Deve buscar perfil quando existir")
    @Order(1)
    void deveBuscarPerfil() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idPerfilExistente)
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
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve buscar perfil quando informar a descrição existente")
    @Order(4)
    void deveBuscarPerfilViaDescricao() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/buscar?descricao=".concat(descricaoExistente)))
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
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
    }
}
