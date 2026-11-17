
package com.ventrya.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Ventrya API")
                        .version("1.0.0")
                        .description("""
                                API REST desenvolvida para a Global Solution
                                2026/2 da FIAP.

                                A Ventrya conecta jovens a desafios
                                profissionais, permitindo desenvolver
                                experiências práticas, receber feedbacks
                                gerados por Inteligência Artificial,
                                construir portfólios e realizar
                                entrevistas simuladas.

                                Tecnologias:
                                - Java 21 e Spring Boot
                                - Spring Security com JWT
                                - Spring Data JPA
                                - Spring AI
                                - RabbitMQ
                                """))
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )
                .components(new Components()
                        .addSecuritySchemes(
                                securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description(
                                                "Informe o token JWT obtido no login."
                                        )
                        ));
    }
}

