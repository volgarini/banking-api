package pt.com.bank.banking_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankingApi() {

        return new OpenAPI()

                .info(new Info()

                        .title("Banking API")

                        .description("""
                                REST API for banking operations.
                                
                                This project was developed as a portfolio project
                                using Spring Boot 3, Java 21, PostgreSQL,
                                Flyway and Docker.
                                """)

                        .version("v0.2.0")

                        .contact(new Contact()

                                .name("Lucas Souza")
                                .email("lucasvolgarini@gmail.com")
                                .url("https://github.com/your-user"))

                        .license(new License()

                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))

                .externalDocs(new ExternalDocumentation()

                        .description("Project Repository")
                        .url("https://github.com/volgarini/banking-api"));
    }

}
