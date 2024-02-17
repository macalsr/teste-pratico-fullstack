package com.simplesdental.contatosapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração para a geração da documentação Swagger.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configuração do OpenAPI para documentação Swagger.
     *
     * @return Instância de OpenAPI configurada.
     */
    @Bean
    public OpenAPI myOpenAPI(){

        // Configuração do servidor
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("URL do servidor no ambiente de desenvolvimento");

        // Informações de contato
        Contact contato = new Contact();
        contato.setEmail("mmariasribeiro@gmail.com");
        contato.setName("Maria Ribeiro");

        // Informações gerais da API
        Info info = new Info()
                .title("API de Gerenciamento de Contatos")
                .version("1.0")
                .description("Esta API expõe endpoints para gerenciar contatos.")
                .contact(contato);

        return new OpenAPI().info(info);
    }
}
