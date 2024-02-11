package br.com.gabrielferreira.eventos.domain.service.security;

import br.com.gabrielferreira.eventos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.service.security.model.InformacoesTokenModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

import static br.com.gabrielferreira.eventos.common.utils.DataUtils.*;

@Service
public class TokenService {

    private final String chave;

    private final String expiracao;

    public TokenService(@Value("${jwt.secret}") String chave, @Value("${jwt.expiration}") String expiracao) {
        this.chave = chave;
        this.expiracao = expiracao;
    }

    public InformacoesTokenModel gerarLoginToken(Authentication authentication){
        String token = gerarToken(authentication);

        if(isTokenValido(token)){
            Claims claims = extrairTodoClaims(token);
            Long idUsuario = Long.valueOf(claims.get("idUsuario").toString());
            long milisegundoDataInicio = Long.parseLong(String.valueOf(claims.get("dataAtualMilisegundo")));
            ZonedDateTime dataInicio = Instant.ofEpochMilli(milisegundoDataInicio).atZone(FUSO_HORARIO_PADRAO_SISTEMA);

            long milisegundoDataFim = Long.parseLong(String.valueOf(claims.get("dataExpiracaoMilisegundo")));
            ZonedDateTime dataFim = Instant.ofEpochMilli(milisegundoDataFim).atZone(FUSO_HORARIO_PADRAO_SISTEMA);

            return InformacoesTokenModel.builder()
                    .idUsuario(idUsuario)
                    .tipo("Bearer")
                    .token(token)
                    .dataInicio(dataInicio)
                    .dataFim(dataFim)
                    .build();
        } else {
            throw new RegraDeNegocioException("Não foi possível logar no sistema pois o token não é válido");
        }
    }

    private String gerarToken(Authentication authentication){
        SecretKey secretKey = getSecret();
        ZonedDateTime dataAtual = ZonedDateTime.now(UTC);
        ZonedDateTime dataExpiracao = dataAtual.plus(Duration.ofMillis(Long.parseLong(expiracao)));
        Usuario usuario = (Usuario) authentication.getPrincipal();

        return Jwts.builder()
                .issuer("API Evento")
                .subject("API de Eventos")
                .issuedAt(Date.from(dataAtual.toInstant()))
                .expiration(Date.from(dataExpiracao.toInstant()))
                .claim("idUsuario", usuario.getId())
                .claim("dataAtualMilisegundo", dataAtual.toInstant().toEpochMilli())
                .claim("dataExpiracaoMilisegundo", dataExpiracao.toInstant().toEpochMilli())
                .signWith(secretKey)
                .compact();
    }

    public Claims extrairTodoClaims(String token){
        return Jwts.parser().verifyWith(getSecret()).build().parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValido(String token){
        try {
            extrairTodoClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private SecretKey getSecret(){
        return Keys.hmacShaKeyFor(chave.getBytes(StandardCharsets.UTF_8));
    }
}
