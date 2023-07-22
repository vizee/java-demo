package com.example.sck;

import com.example.sck.feign.SbkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@RestController
@EnableFeignClients
@SpringBootApplication
public class SckApplication {

    static final Logger log = LoggerFactory.getLogger(SckApplication.class);

    @Value("${app.version:}")
    String version;

    @Autowired
    SbkClient sbkClient;

    @GetMapping("/hello")
    String hello() {
        log.info("call by OpenFeign");
        log.info("config version: {}", version);
        return sbkClient.hello();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/now")
    String now() {
        log.info("call by RestTemplate");
        return restTemplate().getForObject("http://sbk-app-svc:8080/now", String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SckApplication.class, args);
    }

}
