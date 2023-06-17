package com.example.demo;

import com.example.demo.grpc.ServiceManager;
import com.example.demo.grpc.annotation.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        var configurableApplicationContext = SpringApplication.run(DemoApplication.class, args);
        var grpcServiceBeanMap = configurableApplicationContext.getBeansWithAnnotation(GrpcService.class);
        var serviceManager = configurableApplicationContext.getBean(ServiceManager.class);
        try {
            serviceManager.start(grpcServiceBeanMap);
            serviceManager.spin();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
