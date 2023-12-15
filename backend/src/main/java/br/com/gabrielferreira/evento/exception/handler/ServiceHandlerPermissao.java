package br.com.gabrielferreira.evento.exception.handler;

import br.com.gabrielferreira.evento.exception.model.ErroPadrao;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.evento.utils.DataUtils.FUSO_HORARIO_PADRAO_SISTEMA;

@ControllerAdvice
@RequiredArgsConstructor
public class ServiceHandlerPermissao implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatus.value());

        ErroPadrao erroPadrao = ErroPadrao.builder()
                .momento(ZonedDateTime.now(FUSO_HORARIO_PADRAO_SISTEMA))
                .status(httpStatus.value())
                .erro("Proibido")
                .mensagem("Você não tem a permissão de realizar esta ação")
                .caminhoUrl(request.getRequestURI())
                .build();

        String json = objectMapper.writeValueAsString(erroPadrao);
        response.getWriter().write(json);
    }
}
