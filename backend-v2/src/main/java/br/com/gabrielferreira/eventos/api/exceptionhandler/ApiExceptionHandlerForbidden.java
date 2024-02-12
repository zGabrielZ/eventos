package br.com.gabrielferreira.eventos.api.exceptionhandler;

import br.com.gabrielferreira.eventos.api.mapper.ErroPadraoMapper;
import br.com.gabrielferreira.eventos.domain.exception.model.ErroPadraoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.eventos.common.utils.DataUtils.toFusoPadraoSistema;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandlerForbidden implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    private final ErroPadraoMapper erroPadraoMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatus.value());

        ErroPadraoModel erroPadraoModel = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Proibido", "Você não tem a permissão de realizar esta ação", request.getRequestURI());

        String json = objectMapper.writeValueAsString(erroPadraoModel);
        response.getWriter().write(json);
    }
}
