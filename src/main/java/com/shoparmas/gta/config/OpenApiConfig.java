package com.shoparmas.gta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI shopArmasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ShopArmas GTA API")
                        .version("v1")
                        .description("API REST para la gestión de la tienda ShopArmas GTA")
                        .contact(new Contact()
                                .name("ShopArmas GTA")));
    }
}
