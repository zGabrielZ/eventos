package br.com.gabrielferreira.evento.exception.handler;

import br.com.gabrielferreira.evento.exception.model.ErroPadrao;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.evento.utils.DataUtils.FUSO_HORARIO_PADRAO_SISTEMA;

@ControllerAdvice
@RequiredArgsConstructor
public class ServiceHandlerAutenticacao implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatus.value());

        ErroPadrao erroPadrao = ErroPadrao.builder()
                .momento(ZonedDateTime.now(FUSO_HORARIO_PADRAO_SISTEMA))
                .status(httpStatus.value())
                .erro("Não autorizado")
                .mensagem("Você precisa fazer login primeiro para executar esta função")
                .caminhoUrl(request.getRequestURI())
                .build();

        String json = objectMapper.writeValueAsString(erroPadrao);
        response.getWriter().write(json);
    }
}
