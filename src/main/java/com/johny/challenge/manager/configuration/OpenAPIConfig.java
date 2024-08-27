package com.johny.challenge.manager.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
    contact = @Contact(
        name="Johny Cruz",
        email="johnycruz00@gmail.com"
    ),
    description = "Application to manage devices",
    title = "Device Manager",
    version = "1.0"
),
servers = {
    @Server(
        description = "Local ENV",
        url = "http://localhost:8080"
    )
},
security = {
    @SecurityRequirement(
        name = "X-API-KEY"
    )
}


)
@SecurityScheme(
    name = "X-API-KEY",
    description = "ApiKey authentication",
    scheme = "X-API-KEY",
    type = SecuritySchemeType.APIKEY,
    in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
    
}
