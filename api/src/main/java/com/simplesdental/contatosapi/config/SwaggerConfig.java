package com.simplesdental.contatosapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI(){

        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("URL do servidor no ambiente de desenvolvimento");

        Contact contato = new Contact();
        contato.setEmail("mmariasribeiro@gmail.com");
        contato.setName("Maria Ribeiro");

        Info info = new Info()
                .title("API de Gerenciamento de Contatos")
                .version("1.0")
                .description("Esta API expõe endpoints para gerenciar contatos.")
                .contact(contato);

        return new OpenAPI().info(info);
    }
}
