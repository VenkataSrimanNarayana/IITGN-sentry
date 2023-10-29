package com.iitgn.entryexit.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI OpenAPI() {

        Components components = new Components();
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.HTTP);
        securityScheme.setScheme("bearer");
        securityScheme.setBearerFormat("JWT");
        securityScheme.setIn(SecurityScheme.In.HEADER);
        securityScheme.setName("Authorization");
        components.addSecuritySchemes("bearer-jwt", securityScheme);
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("bearer-jwt");
        List<SecurityRequirement> securityRequirementList = new ArrayList<>();
        securityRequirementList.add(securityRequirement);

        return new OpenAPI()
                .info(new Info().title("EntryExit API")
                        .description("This api is used entry exit register")
                        .version("1.0").license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.txt"))).components(components).security(securityRequirementList);
    }
}