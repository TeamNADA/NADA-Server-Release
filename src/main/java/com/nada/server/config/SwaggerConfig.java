package com.nada.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean // 스웨거 테스트를 위한 설정입니다
    public Docket testapi(){
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("Test API")
            .apiInfo(new ApiInfoBuilder()
                .title("스웨거 테스트용")
                .description("스웨거 테스트 하기 위한 API문서")
                .version("0.0.0")
                .build()
            )
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.nada.server.controller"))
            .paths(PathSelectors.any())
            .build();

    }
}
