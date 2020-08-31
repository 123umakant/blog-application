package com.mountblue.blogpost.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration

public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mountblue.blogpost.controller"))
                .paths(postPaths())
                .build()
                .apiInfo(metaInfo());
    }

    private Predicate<String> postPaths() {
        return or(regex("/*"), or(regex("/api/auth.*"), regex("/comment.*"),
                or(regex("/post.*"),or(regex("/tag.*")))));
    }


    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Blog Post API",
                "Blog Post Implementation",
                "1.0",
                "Terms of Service",
                new Contact("Umakant", "#",
                        "umakant.un@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html"
        );

        return apiInfo;
    }



}
