package br.com.gabrielferreira.eventos.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI(){
        Contact contact = new Contact();
        contact.setName("Gabriel Ferreira");
        contact.setEmail("ferreiragabriel2612@gmail.com");
        contact.setUrl("https://github.com/zGabrielZ");

        Info info = new Info()
                .title("API Evento")
                .version("1.0")
                .contact(contact)
                .description("API de Eventos");

        String nameSecurityScheme = "bearerAuth";
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");

        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement()
                        .addList(nameSecurityScheme))
                .components(new Components()
                        .addSecuritySchemes(nameSecurityScheme, securityScheme));
    }
}
