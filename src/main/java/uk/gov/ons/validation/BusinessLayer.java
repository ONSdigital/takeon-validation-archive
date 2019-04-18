package uk.gov.ons.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
//@EnableFeignClients
@SpringBootApplication
@RibbonClient(name="validationengine")
public class BusinessLayer {
    public static void main(String[] args) {
        SpringApplication.run(BusinessLayer.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate(){return new RestTemplate();}
}
