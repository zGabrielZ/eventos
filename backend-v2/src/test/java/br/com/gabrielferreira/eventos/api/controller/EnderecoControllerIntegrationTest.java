package br.com.gabrielferreira.eventos.api.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnderecoControllerIntegrationTest {

    private static final String URL = "/enderecos";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    @ParameterizedTest
    @ValueSource(strings = {"123", "999999", "999999999"})
    @DisplayName("Não deve buscar cep quando informar diferente de 8 caracteres")
    @Order(1)
    void naoDeveBuscarCepQuandoInformarCepDiferenteOitoCaracteres(String cep) throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("?cep=").concat(cep))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.mensagem").value("O cep tem que ter no máximo de 8 caracteres"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"99av9999", "abcdefgh", "ABC99999"})
    @DisplayName("Não deve buscar cep quando informar cep não digito")
    @Order(2)
    void naoDeveBuscarCepQuandoInformarCepNaoDigito(String cep) throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("?cep=").concat(cep))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.mensagem").value("O cep tem que ter somente dígitos"));
    }

    @Test
    @DisplayName("Não deve buscar cep quando informar cep inexistente")
    @Order(3)
    void naoDeveBuscarCepQuandoInformarInexistente() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("?cep=").concat("99999999"))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.mensagem").value("Cep informado é inexistente"));
    }

    @Test
    @DisplayName("Deve buscar cep quando informar cep existente")
    @Order(4)
    void deveBuscarCepQuandoInformarCepExistente() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("?cep=").concat("01451000"))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.cep").value("01451000"));
        resultActions.andExpect(jsonPath("$.logradouro").exists());
        resultActions.andExpect(jsonPath("$.complemento").exists());
        resultActions.andExpect(jsonPath("$.bairro").exists());
        resultActions.andExpect(jsonPath("$.localidade").exists());
        resultActions.andExpect(jsonPath("$.uf").exists());
    }
}
