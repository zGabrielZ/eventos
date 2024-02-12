package br.com.gabrielferreira.eventos.api.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private Long idPerfilExistente;

    private Long idPerfilInexistente;

    @BeforeEach
    void setUp(){
        idPerfilExistente = 1L;
        idPerfilInexistente = -1L;
    }

    @Test
    @DisplayName("Deve buscar lista de perfis quando existir")
    @Order(1)
    void deveBuscarListaDePerfis() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL)
                        .accept(MEDIA_TYPE_JSON));
        resultActions.andExpect(status().isOk());

        String conteudo = resultActions.andReturn().getResponse().getContentAsString();
        assertNotEquals("[]", conteudo);
    }

    @Test
    @DisplayName("Deve buscar perfil por id quando existir dado informado")
    @Order(2)
    void deveBuscarPerfilPorId() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idPerfilExistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.descricao").exists());
        resultActions.andExpect(jsonPath("$.autoriedade").exists());
    }

    @Test
    @DisplayName("Não deve buscar perfil por id quando não existir dado informado")
    @Order(3)
    void naoDeveBuscarPerfilPorId() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idPerfilInexistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Perfil não encontrado"));
    }
}
