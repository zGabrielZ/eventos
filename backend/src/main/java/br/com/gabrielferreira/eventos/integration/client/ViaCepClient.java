package br.com.gabrielferreira.eventos.integration.client;

import br.com.gabrielferreira.eventos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.eventos.integration.model.CepIntegrationModel;
import io.netty.channel.ChannelOption;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@Slf4j
public class ViaCepClient {

    private final WebClient webClient;

    private final String baseUrl;

    public ViaCepClient(@Value("${via.cep}") String baseUrl) {
        this.baseUrl = baseUrl;

        HttpClient httpClient = HttpClient.create()
                .resolver(DefaultAddressResolverGroup.INSTANCE)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000);

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public CepIntegrationModel buscarCep(String cep){
        log.info("Realizando a consulta na uri {} e o Cep {}", this.baseUrl, cep);
        CepIntegrationModel cepIntegrationModel = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("{cep}/json").build(cep))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class).
                        flatMap(error -> {
                            log.warn("Cep informado é invalido {}", cep);
                            log.warn("Erro {}", error);
                            throw new RegraDeNegocioException("Cep informado é invalido");
                        }))
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.warn("Erro interno ao informar o cep {} ", cep);
                    return Mono.error(new RegraDeNegocioException("Erro interno ao informar o Cep"));
                })
                .bodyToMono(CepIntegrationModel.class)
                .block();

        if(cepIntegrationModel != null && cepIntegrationModel.isErro()){
            log.warn("Cep informado é inexistente {}", cep);
            throw new RegraDeNegocioException("Cep informado é inexistente");
        }

        log.info("Finalizando a consulta na uri {} e o Cep {}", this.baseUrl, cep);

        return cepIntegrationModel;
    }
}
