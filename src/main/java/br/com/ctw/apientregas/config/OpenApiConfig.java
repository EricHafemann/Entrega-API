package br.com.ctw.apientregas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configureSwagger () {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestão de Entregas")
                        .description("Aplicação backend em Spring Boot para a gestão de entregas e motoristas, estruturando as rotas REST.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Eric Gabriel Hafemann")
                                .email("eric070gabriel@gmail.com")
                        )
                );
    }
}
