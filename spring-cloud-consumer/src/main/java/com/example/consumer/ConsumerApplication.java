package com.example.consumer;

import com.example.consumer.feign.DemoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient(autoRegister = false)
@EnableFeignClients
@SpringBootApplication
public class ConsumerApplication {

	static final Logger log = LoggerFactory.getLogger(ConsumerApplication.class);

	@Autowired
	DemoClient demoClient;

	@GetMapping("/invoke")
	String invoke() {
		log.info("invoke");
		return demoClient.getHello();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
