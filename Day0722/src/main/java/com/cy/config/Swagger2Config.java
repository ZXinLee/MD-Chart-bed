package com.cy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * 本文件需要修改的信息 : controller的路径
 *
 * 页面请求: http://localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cy.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("CGB210404","url","1832446529@qq.com");
        return new ApiInfo("微服务SwaggerAPI文档",
                "好好学习，天天向上！",
                "v1.0",
                "URL",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }
}
