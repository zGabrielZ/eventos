package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.model.input.PerfilIdInputModel;
import br.com.gabrielferreira.eventos.api.model.input.UsuarioInputModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static br.com.gabrielferreira.eventos.tests.UsuarioFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllerIntegrationTest {

    private static final String URL = "/usuarios";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;
    private static final String SENHA_COM_DIGITO = "123";
    private static final String SENHA_COM_CARACTERE_ESPECIAL = "@aaa";
    private static final String SENHA_COM_CARACTERE_ESPECIAL_MINUSCULAS_MAIUSCULAS = "@AAAaa";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private UsuarioInputModel input;

    private Long idUsuarioExistente;

    private Long idUsuarioInexsitente;

    private UsuarioInputModel inputAtualizar;

    @BeforeEach
    void setUp(){
        input = criarUsuarioInput();
        idUsuarioExistente = 1L;
        idUsuarioInexsitente = -1L;
        inputAtualizar = criarUsuarioInputAtualizar();
    }

    @Test
    @DisplayName("Deve cadastrar usuário quando informar dados")
    @Order(1)
    void deveCadastrarUsuarioQuandoInformarDados() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(input);

        String nomeEsperado = input.getNome();
        String emailEsperado = input.getEmail();

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
        resultActions.andExpect(jsonPath("$.email").value(emailEsperado));
        resultActions.andExpect(jsonPath("$.perfis").exists());
        resultActions.andExpect(jsonPath("$.dataCadastro").exists());
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando não informar dados")
    @Order(2)
    void naoDeveCadastrarUsuarioQuandoNaoInformarDados() throws Exception{
        input.setNome(null);
        input.setSenha(null);
        input.setEmail(null);
        input.setPerfis(null);

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Erro validação de campos"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Ocorreu um erro de validação nos campos"));
        resultActions.andExpect(jsonPath("$.campos").exists());
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando não tem senha sem caractere especial")
    @Order(3)
    void naoDeveCadastrarUsuarioQuandoNaoInformarSenhaSemCaractereEspecial() throws Exception{
        input.setSenha(SENHA_COM_DIGITO);

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("A senha informada tem que ter pelo menos uma caractere especial"));
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando não tem senha sem caractere maiusculas")
    @Order(4)
    void naoDeveCadastrarUsuarioQuandoNaoInformarSenhaSemCaractereMaiusculas() throws Exception{
        input.setSenha(SENHA_COM_CARACTERE_ESPECIAL);

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("A senha informada tem que ter pelo menos uma caractere maiúsculas"));
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando não tem senha sem caractere minusculas")
    @Order(5)
    void naoDeveCadastrarUsuarioQuandoNaoInformarSenhaSemCaractereMinusculas() throws Exception{
        input.setSenha(SENHA_COM_CARACTERE_ESPECIAL.toUpperCase());

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("A senha informada tem que ter pelo menos uma caractere minúsculas"));
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando não tem senha sem caractere com digito")
    @Order(6)
    void naoDeveCadastrarUsuarioQuandoNaoInformarSenhaSemCaractereComDigito() throws Exception{
        input.setSenha(SENHA_COM_CARACTERE_ESPECIAL_MINUSCULAS_MAIUSCULAS);

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("A senha informada tem que ter pelo menos um caractere dígito"));
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando informar email existente")
    @Order(7)
    void naoDeveCadastrarUsuarioQuandoInformarEmailExistente() throws Exception{
        input.setEmail("jose@email.com");

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Este e-mail 'jose@email.com' já foi cadastrado"));
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando informar perfis duplicados")
    @Order(8)
    void naoDeveCadastrarUsuarioQuandoInformarPerfisDuplicados() throws Exception{
        PerfilIdInputModel perfil1 = input.getPerfis().get(0);
        PerfilIdInputModel perfil2 = input.getPerfis().get(0);
        input.setPerfis(Arrays.asList(perfil1, perfil2));

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Não vai ser possível cadastrar este usuário pois tem perfis duplicados ou mais de duplicados"));
    }

    @Test
    @DisplayName("Não deve cadastrar usuário quando informar perfis inexistente")
    @Order(9)
    void naoDeveCadastrarUsuarioQuandoInformarPerfisInexistente() throws Exception{
        PerfilIdInputModel perfil1 = input.getPerfis().get(0);
        perfil1.setId(-1L);
        input.setPerfis(List.of(perfil1));

        String jsonBody = objectMapper.writeValueAsString(input);

        ResultActions resultActions = mockMvc
                .perform(post(URL)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.titulo").value("Não encontrado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Perfil não encontrado"));
    }

    @Test
    @DisplayName("Deve buscar usuário por id quando existir dado informado")
    @Order(10)
    void deveBuscarUsuarioPorId() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idUsuarioExistente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.nome").exists());
        resultActions.andExpect(jsonPath("$.email").exists());
        resultActions.andExpect(jsonPath("$.perfis").exists());
        resultActions.andExpect(jsonPath("$.dataCadastro").exists());
    }

    @Test
    @DisplayName("Não deve buscar usuário por id quando não existir dado informado")
    @Order(11)
    void naoDeveBuscarUsuarioPorId() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get(URL.concat("/{id}"), idUsuarioInexsitente)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }

    @Test
    @DisplayName("Deve atualizar usuário quando informar dados")
    @Order(12)
    void deveAtualizarUsuarioQuandoInformarDados() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(inputAtualizar);

        Long idEsperado = idUsuarioExistente;
        String nomeEsperado = inputAtualizar.getNome();
        String emailEsperado = inputAtualizar.getEmail();

        ResultActions resultActions = mockMvc
                .perform(put(URL.concat("/{id}"), idUsuarioExistente)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").value(idEsperado));
        resultActions.andExpect(jsonPath("$.nome").value(nomeEsperado));
        resultActions.andExpect(jsonPath("$.email").value(emailEsperado));
        resultActions.andExpect(jsonPath("$.perfis").exists());
        resultActions.andExpect(jsonPath("$.dataCadastro").exists());
    }

    @Test
    @DisplayName("Não deve atualizar usuário quando informar email já existente")
    @Order(13)
    void naoDeveAtualizarUsuarioQuandoInformarEmailJaExistente() throws Exception{
        inputAtualizar.setEmail("marcos@email.com");
        String jsonBody = objectMapper.writeValueAsString(inputAtualizar);

        ResultActions resultActions = mockMvc
                .perform(put(URL.concat("/{id}"), idUsuarioExistente)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.mensagem").value("Este e-mail 'marcos@email.com' já foi cadastrado"));
    }
}
