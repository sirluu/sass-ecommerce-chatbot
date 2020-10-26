package com.luu.telemed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 
 * @author titchip
 *
 */

@Configuration
@EnableSwagger2
public class RestDocConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.luu.telemed.controllers"))
        .paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {

    return new ApiInfoBuilder().title("TeleMed REST API").description("TeleMed Projet Rest API ")
        .contact(new Contact("HungLQ", "github.com/sirluu", "hunglq7130@github.com"))
        .license("HungLQ ").licenseUrl("http://www.luuquanghung.com/licenses/LICENSE-2.0.html")
        .version("1.0.0").build();
  }

}
