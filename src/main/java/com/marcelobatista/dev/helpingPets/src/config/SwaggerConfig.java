package com.marcelobatista.dev.helpingPets.src.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API To Help Pets", description = "Our app provides differents ways to help pets withou a home for example, as well as to adopt one", version = "1.0.0", contact = @Contact(name = "Marcelo Batista", email = "chelobat16411@gmail.com", url = "www.marcelobatista.dev"), license = @License(name = "Standard Software Use License for marcelobatista.dev", url = "marcelobatista.dev/license")), servers = {
    @Server(description = "DEV SERVER", url = "http://localhost:8082/api/v1"),
    @Server(description = "PROD SERVER", url = "http://localhost:8082/api/v1")
})
@SecuritySchemes({
    @SecurityScheme(name = "oauth2", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(authorizationCode = @OAuthFlow(authorizationUrl = "http://localhost:8082/api/v1/oauth2/authorization/google", tokenUrl = "http://localhost:8082/api/v1/login/oauth2/code/google"))),
    @SecurityScheme(name = "cookieAuth", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.COOKIE, paramName = "JSESSIONID")
})
public class SwaggerConfig {
}
