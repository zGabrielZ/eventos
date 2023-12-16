package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.dto.request.LoginRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static br.com.gabrielferreira.evento.tests.LoginFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp(){
        loginRequestDTO = criarLogin("teste@email.com", "123");
    }

    @Test
    @DisplayName("Deve realizar login quando existir dados")
    @Order(1)
    void deveRealizarLoginQuandoExistirDados() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(loginRequestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.idUsuario").exists());
        resultActions.andExpect(jsonPath("$.tipo").exists());
        resultActions.andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("Não deve realizar login quando não existir email")
    @Order(2)
    void naoDeveRealizarLoginQuandoNaoExistirEmail() throws Exception{
        loginRequestDTO.setEmail("teste244@email.com");
        String jsonBody = objectMapper.writeValueAsString(loginRequestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.erro").value("Não encontrado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("E-mail teste244@email.com não encontrado"));
    }

    @Test
    @DisplayName("Não deve realizar login quando senha informada não combinar com a senha já cadastrada")
    @Order(3)
    void naoDeveRealizarLoginQuandoSenhaNaoCombinar() throws Exception{
        loginRequestDTO.setSenha("1111111111111");
        String jsonBody = objectMapper.writeValueAsString(loginRequestDTO);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Senha inválida"));
    }
}
