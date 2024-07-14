package org.serp.booklending.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

import java.util.Locale;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Kalil ALMAASALI",
                        email = "kalmaasaly@gmail.com",
                        url="https://www.linkedin.com/in/kalmaasali/"
                ),
                description = "OpenApi documentation for spring ",
                title = "OpenApi specification ",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local Environment ",
                        url = "http://localhost:8088/api/v1"
                )
        },
        security = {
               @SecurityRequirement(
                       name = "bearerAuth"
               )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWR auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER

)
public class OpenAPIDefinitionConfig {
}
