package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.model.input.LoginInputModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static br.com.gabrielferreira.eventos.tests.LoginFactory.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginControllerIntegrationTest {

    private static final String URL = "/login";

    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private LoginInputModel loginInputModel;

    @BeforeEach
    void setUp(){
        loginInputModel = criarLogin("jose@email.com", "123");
    }

    @Test
    @DisplayName("Deve realizar login quando existir dados")
    @Order(1)
    void deveRealizarLoginQuandoExistirDados() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(loginInputModel);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.idUsuario").exists());
        resultActions.andExpect(jsonPath("$.tipo").exists());
        resultActions.andExpect(jsonPath("$.tipo").value("Bearer"));
        resultActions.andExpect(jsonPath("$.token").exists());
        resultActions.andExpect(jsonPath("$.dataInicio").exists());
        resultActions.andExpect(jsonPath("$.dataFim").exists());
    }

    @Test
    @DisplayName("Não deve realizar login quando não existir email")
    @Order(2)
    void naoDeveRealizarLoginQuandoNaoExistirEmail() throws Exception{
        loginInputModel.setEmail("teste244@email.com");
        String jsonBody = objectMapper.writeValueAsString(loginInputModel);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.titulo").value("Não encontrado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("E-mail teste244@email.com não encontrado"));
    }

    @Test
    @DisplayName("Não deve realizar login quando senha informada não combinar com a senha já cadastrada")
    @Order(3)
    void naoDeveRealizarLoginQuandoSenhaNaoCombinar() throws Exception{
        loginInputModel.setSenha("1111111111111");
        String jsonBody = objectMapper.writeValueAsString(loginInputModel);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Senha inválida"));
    }

    @Test
    @DisplayName("Deve realizar refresh token do usuário autenticado")
    @Order(4)
    void deveRealizarRefreshTokenUsuarioLogado() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(loginInputModel);

        ResultActions resultActionsLogin = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        String response = resultActionsLogin.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String token = jsonParser.parseMap(response).get("token").toString();

        ResultActions resultActionsRefresh = mockMvc
                .perform(post(URL.concat("/refresh-token"))
                        .header("Authorization", "Bearer " + token)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActionsRefresh.andExpect(status().isOk());
        resultActionsRefresh.andExpect(jsonPath("$.idUsuario").exists());
        resultActionsRefresh.andExpect(jsonPath("$.tipo").exists());
        resultActionsRefresh.andExpect(jsonPath("$.tipo").value("Bearer"));
        resultActionsRefresh.andExpect(jsonPath("$.token").exists());
        resultActionsRefresh.andExpect(jsonPath("$.dataInicio").exists());
        resultActionsRefresh.andExpect(jsonPath("$.dataFim").exists());
    }

    @Test
    @DisplayName("Não deve realizar refresh token quando tiver token inválido ou usuário não encontrado")
    @Order(5)
    void naoDeveRealizarRefreshTokenQuandoTokenInvalido() throws Exception{
        ResultActions resultActions = mockMvc
                .perform(post(URL.concat("/refresh-token"))
                        .header("Authorization", "Bearer 123invalido123")
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isUnauthorized());
        resultActions.andExpect(jsonPath("$.titulo").value("Não autorizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Usuário inválido"));
    }
}
