package com.ioet.bpm.skills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        HashSet<String> protocols = new HashSet<>();
        protocols.add("http");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/skills.*"))
                .build()
                .apiInfo(metaData())
                .protocols(protocols);
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("BPM Skills API")
                .description("Manage skills in bpm-skills-project")
                .version("0.0.1")
                .build();
    }
}
