package br.com.gabrielferreira.eventos.api.controller;

import org.junit.jupiter.api.*;
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
class UsuarioPerfilControllerIntegrationTest {

    private static final String URL = "/usuarios";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;
    private static final String FILTRO_USUARIO_EXISTENTE = URL.concat("/1").concat("/perfis");
    private static final String FILTRO_USUARIO_INEXISTENTE = URL.concat("/-1").concat("/perfis");

    @Autowired
    protected MockMvc mockMvc;

    @Test
    @DisplayName("Deve buscar perfis quando informar usuário existente")
    @Order(1)
    void deveBuscarPerfisQuandoInformarUsuarioExistente() throws Exception {
        String filtro = FILTRO_USUARIO_EXISTENTE.concat("?page=0&size=5&sort=id,desc&sort=descricao,desc&sort=autoriedade,desc");

        ResultActions resultActions = mockMvc
                .perform(get(filtro)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }

    @Test
    @DisplayName("Deve buscar perfis quando informar usuário existente filtros existente")
    @Order(2)
    void deveBuscarPerfisQuandoInformarFiltro() throws Exception {
        String filtro = FILTRO_USUARIO_EXISTENTE.concat("?page=0&size=5&id=1&descricao=Adm&autoriedade=ROLE_");

        ResultActions resultActions = mockMvc
                .perform(get(filtro)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }

    @Test
    @DisplayName("Não deve buscar perfis quando informar usuário inexistente")
    @Order(3)
    void naoDeveBuscarPerfisQuandoInformarUsuarioInexsistente() throws Exception{
        ResultActions resultActions = mockMvc
                .perform(get(FILTRO_USUARIO_INEXISTENTE)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }
}
