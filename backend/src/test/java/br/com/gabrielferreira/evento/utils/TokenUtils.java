package br.com.gabrielferreira.evento.utils;

import br.com.gabrielferreira.evento.dto.request.LoginRequestDTO;
import br.com.gabrielferreira.evento.exception.MsgException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static br.com.gabrielferreira.evento.tests.LoginFactory.*;

@Component
public class TokenUtils {

    @Autowired
    protected ObjectMapper objectMapper;

    public String gerarToken(MockMvc mockMvc, String email, String senha){
        LoginRequestDTO loginDTO = criarLogin(email, senha);
        try {
            String jsonBody = objectMapper.writeValueAsString(loginDTO);

            ResultActions resultActions = mockMvc
                    .perform(post("/login")
                            .content(jsonBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON));
            String resultado = resultActions.andReturn().getResponse().getContentAsString();

            JacksonJsonParser jsonParser = new JacksonJsonParser();
            return jsonParser.parseMap(resultado).get("token").toString();
        } catch (Exception e){
            throw new MsgException("Erro ao logar usuário");
        }
    }
}
