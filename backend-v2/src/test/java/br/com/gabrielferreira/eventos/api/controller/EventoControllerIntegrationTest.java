package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.model.input.EventoInputModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static br.com.gabrielferreira.eventos.tests.EventoFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventoControllerIntegrationTest {

    private static final String URL = "/usuarios/";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private EventoInputModel eventoInput;

    private Long idUsuarioExistente;

    @BeforeEach
    void setUp(){
        eventoInput = criarEventoInput();
        idUsuarioExistente = 1L;
    }

    @Test
    @DisplayName("Deve cadastrar um evento")
    @Order(1)
    void deveCadastrarEvento() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(eventoInput);

        String nomeEsperado = eventoInput.getNome();
        String dataEsperado = eventoInput.getData().toString();
        String urlEsperado = eventoInput.getUrl();
        String localidadeEsperado = "São Paulo";

        ResultActions resultActions = mockMvc
                .perform(post(URL.concat("{idUsuario}/eventos"), idUsuarioExistente)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
        resultActions.andExpect(jsonPath("$.data").value(dataEsperado));
        resultActions.andExpect(jsonPath("$.url").value(urlEsperado));
        resultActions.andExpect(jsonPath("$.cidade.localidade").value(localidadeEsperado));
        resultActions.andExpect(jsonPath("$.dataCadastro").exists());
    }

    @Test
    @DisplayName("Não deve cadastrar evento quando não informar nome evento já existente")
    @Order(2)
    void naoDeveCadastrarEventoQuandoTiverNomeCadastrado() throws Exception{
        eventoInput.setNome("Feira do Software");
        String jsonBody = objectMapper.writeValueAsString(eventoInput);

        ResultActions resultActions = mockMvc
                .perform(post(URL.concat("{idUsuario}/eventos"), idUsuarioExistente)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Não vai ser possível cadastrar este evento pois o nome 'Feira do Software' já foi cadastrado"));
    }

//    @Test
//    @DisplayName("Deve buscar evento quando existir")
//    @Order(2)
//    void deveBuscarEvento() throws Exception {
//        ResultActions resultActions = mockMvc
//                .perform(get(URL.concat("/{id}"), idEventoExistente)
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isOk());
//        resultActions.andExpect(jsonPath("$.id").exists());
//        resultActions.andExpect(jsonPath("$.nome").exists());
//        resultActions.andExpect(jsonPath("$.data").exists());
//        resultActions.andExpect(jsonPath("$.url").exists());
//        resultActions.andExpect(jsonPath("$.cidade.id").exists());
//        resultActions.andExpect(jsonPath("$.createdAt").exists());
//    }
//
//    @Test
//    @DisplayName("Não deve buscar evento quando não existir")
//    @Order(3)
//    void naoDeveBuscarEvento() throws Exception {
//        ResultActions resultActions = mockMvc
//                .perform(get(URL.concat("/{id}"), idEventoInexistente)
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isNotFound());
//        resultActions.andExpect(jsonPath("$.mensagem").value("Evento não encontrado"));
//    }
//
//    @Test
//    @DisplayName("Deve alterar evento quando existir")
//    @Order(4)
//    void deveAlterarEvento() throws Exception {
//        String jsonBody = objectMapper.writeValueAsString(eventoUpdateDTO);
//
//        Long idEsperado = idEventoExistente;
//        String nomeEsperado = eventoUpdateDTO.getNome();
//        String dataEsperado = eventoUpdateDTO.getData().toString();
//
//        ResultActions resultActions = mockMvc
//                .perform(put(URL.concat("/{id}"), idEventoExistente)
//                        .header("Authorization", "Bearer " + tokenAdmin)
//                        .content(jsonBody)
//                        .contentType(MEDIA_TYPE_JSON)
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isOk());
//        resultActions.andExpect(jsonPath("$.id").value(idEsperado));
//        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
//        resultActions.andExpect(jsonPath("$.data").value(dataEsperado));
//        resultActions.andExpect(jsonPath("$.url").exists());
//        resultActions.andExpect(jsonPath("$.cidade.id").exists());
//        resultActions.andExpect(jsonPath("$.createdAt").exists());
//    }
//
//    @Test
//    @DisplayName("Não deve alterar evento quando não existir")
//    @Order(5)
//    void naoDeveAlterarEvento() throws Exception {
//        String jsonBody = objectMapper.writeValueAsString(eventoUpdateDTO);
//
//        ResultActions resultActions = mockMvc
//                .perform(put(URL.concat("/{id}"), idEventoInexistente)
//                        .header("Authorization", "Bearer " + tokenAdmin)
//                        .content(jsonBody)
//                        .contentType(MEDIA_TYPE_JSON)
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isNotFound());
//        resultActions.andExpect(jsonPath("$.mensagem").value("Evento não encontrado"));
//    }
//
//    @Test
//    @DisplayName("Deve deletar evento quando existir")
//    @Order(6)
//    void deveDeletarEvento() throws Exception {
//        ResultActions resultActions = mockMvc
//                .perform(delete(URL.concat("/{id}"), idEventoExistente)
//                        .header("Authorization", "Bearer " + tokenAdmin)
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isNoContent());
//    }
//
//    @Test
//    @DisplayName("Não deve deletar evento quando não existir")
//    @Order(7)
//    void naoDeveDeletarEvento() throws Exception {
//        ResultActions resultActions = mockMvc
//                .perform(delete(URL.concat("/{id}"), idEventoInexistente)
//                        .header("Authorization", "Bearer " + tokenAdmin)
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isNotFound());
//        resultActions.andExpect(jsonPath("$.mensagem").value("Evento não encontrado"));
//    }
//
//    @Test
//    @DisplayName("Deve buscar eventos paginados quando existir")
//    @Order(8)
//    void deveBuscarEventosPaginados() throws Exception {
//        ResultActions resultActions = mockMvc
//                .perform(get(URL.concat("?page=0&size=5&sort=id,desc"))
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isOk());
//        resultActions.andExpect(jsonPath("$.content").exists());
//    }
//
//    @Test
//    @DisplayName("Não deve buscar eventos paginados quando informar propriedade incorreta")
//    @Order(9)
//    void naoDeveBuscarEventosPaginadosQuandoInformarPropriedadesIncorreta() throws Exception {
//        ResultActions resultActions = mockMvc
//                .perform(get(URL.concat("?page=0&size=5&sort=iddd,desc"))
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isBadRequest());
//        resultActions.andExpect(jsonPath("$.mensagem").value("A propriedade informada 'iddd' não existe"));
//    }
//
//    @Test
//    @DisplayName("Deve buscar eventos paginados quando informar propriedade diferente do dto com a entidade")
//    @Order(10)
//    void deveBuscarEventosPaginadosPropriedadeDiferente() throws Exception {
//        ResultActions resultActions = mockMvc
//                .perform(get(URL.concat("?page=0&size=5&sort=data,desc"))
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isOk());
//        resultActions.andExpect(jsonPath("$.content").exists());
//    }
//
//    @Test
//    @DisplayName("Deve buscar eventos avançados paginados quando existir")
//    @Order(11)
//    void deveBuscarEventosAvancadosPaginados() throws Exception {
//        String filtro = "/avancada?page=0&size=5&sort=id,desc&id=4&nome=Semana&data=2021-05-03" +
//                "&url=https://devsuperior.com.br&idCidade=3&createdAt=2023-09-26&updatedAt=";
//
//        ResultActions resultActions = mockMvc
//                .perform(get(URL.concat(filtro))
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isOk());
//        resultActions.andExpect(jsonPath("$.content").exists());
//    }

//    @Test
//    @DisplayName("Não deve alterar evento quando informar nome já existente")
//    @Order(14)
//    void naoDeveAlterarEventoQuandoTiverNomeEventoJaExistente() throws Exception {
//        eventoUpdateDTO.setNome("CCXP");
//        String jsonBody = objectMapper.writeValueAsString(eventoUpdateDTO);
//
//        ResultActions resultActions = mockMvc
//                .perform(put(URL.concat("/{id}"), idEventoExistente)
//                        .header("Authorization", "Bearer " + tokenAdmin)
//                        .content(jsonBody)
//                        .contentType(MEDIA_TYPE_JSON)
//                        .accept(MEDIA_TYPE_JSON));
//
//        resultActions.andExpect(status().isBadRequest());
//        resultActions.andExpect(jsonPath("$.erro").value("Erro personalizado"));
//        resultActions.andExpect(jsonPath("$.mensagem").value("Não vai ser possível atualizar este evento pois o nome 'CCXP' já foi cadastrado"));
//    }
}
