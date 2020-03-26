package guo.cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description:
 * @author: guoyiming
 **/
@SpringBootApplication(scanBasePackages = "guo.cars")
@EnableSwagger2
@Configuration
public class CarsApplication {

    public static void main(String[] args){
        SpringApplication.run(CarsApplication.class,args);
    }
}
