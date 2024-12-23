package com.example.outpatient.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableKnife4j
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        String swaggerUrl = "http://localhost:8080/doc.html";
        License license = new License();
        license.setName("Apache License Version 2.0");
        license.setUrl("https://github.com/springfox/springfox/blob/master/LICENSE");
        return new OpenAPI().info(new Info()
                .title("Outpatient Service Api")
                .description("API Interface")
                .license(license)
                .contact(new Contact().url(swaggerUrl).name("huang"))
                .version("1.0.0"));
    }
}
