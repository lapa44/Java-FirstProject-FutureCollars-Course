package com.futurecollars.accounting.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors
            .basePackage("com.futurecollars.accounting"))
        .paths(PathSelectors.any())
        .build()
        .consumes(Collections.singleton("application/json"))
        .produces(Collections.singleton("application/json"))
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Accounting application.",
        "The API allows to generate and manage invoices.",
        "1.0",
        "Terms of service",
        new Contact("P-16", "https://futurecollars.com/pl/",
            "hello@futurecollars.com"),
        "License of API", "API license URL", Collections.emptyList());
  }
}