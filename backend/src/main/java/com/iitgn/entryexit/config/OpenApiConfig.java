package com.iitgn.entryexit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .info(new Info().title("EntryExit API")
                        .description("This api is used entry exit register")
                        .version("1.0").license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.txt")));
    }
}