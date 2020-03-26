package guo.cars.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @description: swagger配置类
 * @author: guoyiming
 **/
public class Swagger2 {
    @Configuration
    public class Swagger {

        @Bean
        public Docket controllerApi() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(new ApiInfoBuilder()
                            .title("基于Opencv的车牌识别系统")
                            .description("郭一鸣-毕业设计")
                            .version("2.0.0")
                            .build())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("guo.cars.controller"))
                    .paths(PathSelectors.any())
                    .build();
        }


    }

}
