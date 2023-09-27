package br.com.gabrielferreira.evento.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CidadeControllerIntegrationTest {

    private static final String URL = "/cidades";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    private Long idCidadeExistente;

    private Long idCidadeInexistente;

    @BeforeEach
    void setUp(){
        idCidadeExistente = 1L;
        idCidadeInexistente = -1L;
    }

    @Test
    @DisplayName("Deve buscar lista de cidades")
    @Order(1)
    void deveBuscarListaDeCidades() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve buscar cidade quando existir")
    @Order(2)
    void deveBuscarCidade() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idCidadeExistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.codigo").exists());
    }

    @Test
    @DisplayName("Não deve buscar cidade quando não existir")
    @Order(3)
    void naoDeveBuscarCidade() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idCidadeInexistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Cidade não encontrada"));
    }

    @Test
    @DisplayName("Deve buscar cidade quando informar o código")
    @Order(4)
    void deveBuscarCidadeQuandoInformarCodigo() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/buscar?codigo=SAO_PAULO"))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.codigo").exists());
    }

    @Test
    @DisplayName("Não deve buscar cidade quando não informar o código")
    @Order(5)
    void naoDeveBuscarCidadeQuandoNaoInformarCodigo() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/buscar?codigo="))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.mensagem").value("É necessário informar o código"));
    }

    @Test
    @DisplayName("Não deve buscar cidade quando informar o código inválido")
    @Order(6)
    void naoDeveBuscarCidadeQuandoInformarCodigoInvalido() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/buscar?codigo=invalido"))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Cidade não encontrada"));
    }

    @Test
    @DisplayName("Não deve buscar cidade quando não informar o código como parametro")
    @Order(7)
    void naoDeveBuscarCidadeQuandoNaoInformarCodigoComoParametro() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/buscar"))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isInternalServerError());
        resultActions.andExpect(jsonPath("$.erro").value("Required request parameter 'codigo' for method parameter type String is not present"));
    }
}
