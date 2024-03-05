package edu.bbte.idde.jdim2141.spring.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    public static final String BEARER_AUTH_TOKEN = "Bearer Auth";

    @Bean
    public OpenAPI openAPI() {
        var components = new Components();

        components.addSecuritySchemes(BEARER_AUTH_TOKEN, new SecurityScheme()
            .type(Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT"));

        return new OpenAPI()
            .info(new Info().title("Catering Server Api"))
            .components(components);
    }
}
