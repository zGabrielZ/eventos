package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.dto.EventoInsertDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static br.com.gabrielferreira.evento.tests.Factory.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventoControllerIntegrationTest {

    private static final String URL = "/eventos";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private Long idEventoExistente;

    private Long idEventoInexistente;

    private EventoInsertDTO eventoInsertDTO;

    private EventoInsertDTO eventoUpdate;

    @BeforeEach
    void setUp(){
        idEventoExistente = 1L;
        idEventoInexistente = -1L;
        eventoInsertDTO = criarEventoInsertDto();
        eventoUpdate = criarEventoUpdate();
    }

    @Test
    @DisplayName("Deve buscar evento quando existir")
    @Order(1)
    void deveBuscarEvento() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idEventoExistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.data").exists());
        resultActions.andExpect(jsonPath("$.url").exists());
        resultActions.andExpect(jsonPath("$.cidade.id").exists());
        resultActions.andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Não deve buscar evento quando não existir")
    @Order(2)
    void naoDeveBuscarEvento() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idEventoInexistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Evento não encontrado"));
    }

    @Test
    @DisplayName("Deve cadastrar um evento")
    @Order(3)
    void deveCadastrarEvento() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(eventoInsertDTO);

        String nomeEsperado = eventoInsertDTO.getNome();
        String dataEsperado = eventoInsertDTO.getData().toString();

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
        resultActions.andExpect(jsonPath("$.data").value(dataEsperado));
        resultActions.andExpect(jsonPath("$.url").exists());
        resultActions.andExpect(jsonPath("$.cidade.id").exists());
        resultActions.andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Deve alterar evento quando existir")
    @Order(4)
    void deveAlterarEvento() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(eventoUpdate);

        Long idEsperado = idEventoExistente;
        String nomeEsperado = eventoUpdate.getNome();
        String dataEsperado = eventoUpdate.getData().toString();

        ResultActions resultActions = mockMvc
                .perform(put(URL.concat("/{id}"), idEventoExistente)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").value(idEsperado));
        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
        resultActions.andExpect(jsonPath("$.data").value(dataEsperado));
        resultActions.andExpect(jsonPath("$.url").exists());
        resultActions.andExpect(jsonPath("$.cidade.id").exists());
        resultActions.andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Não deve alterar evento quando não existir")
    @Order(5)
    void naoDeveAlterarEvento() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(eventoUpdate);

        ResultActions resultActions = mockMvc
                .perform(put(URL.concat("/{id}"), idEventoInexistente)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Evento não encontrado"));
    }

    @Test
    @DisplayName("Deve deletar evento quando existir")
    @Order(6)
    void deveDeletarEvento() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(delete(URL.concat("/{id}"), idEventoExistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Não deve deletar evento quando não existir")
    @Order(7)
    void naoDeveDeletarEvento() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(delete(URL.concat("/{id}"), idEventoInexistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Evento não encontrado"));
    }

    @Test
    @DisplayName("Deve buscar eventos paginados quando existir")
    @Order(8)
    void deveBuscarEventosPaginados() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("?page=0&size=5&sort=id,desc"))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }

    @Test
    @DisplayName("Não deve buscar eventos paginados quando informar propriedade incorreta")
    @Order(9)
    void naoDeveBuscarEventosPaginadosQuandoInformarPropriedadesIncorreta() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("?page=0&size=5&sort=iddd,desc"))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.mensagem").value("A propriedade informada iddd não existe"));
    }

    @Test
    @DisplayName("Deve buscar eventos paginados quando informar propriedade diferente do dto com a entidade")
    @Order(10)
    void deveBuscarEventosPaginadosPropriedadeDiferente() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("?page=0&size=5&sort=data,desc"))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }

    @Test
    @DisplayName("Deve buscar eventos avançados paginados quando existir")
    @Order(11)
    void deveBuscarEventosAvancadosPaginados() throws Exception {
        String filtro = "/avancada?page=0&size=5&sort=id,desc&id=4&nome=Semana&data=2021-05-03" +
                "&url=https://devsuperior.com.br&idCidade=3&createdAt=2023-09-26&updatedAt=";

        ResultActions resultActions = mockMvc
                .perform(get(URL.concat(filtro))
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }
}
