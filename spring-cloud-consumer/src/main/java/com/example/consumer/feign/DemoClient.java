package com.example.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("demo")
public interface DemoClient {

    @GetMapping("/hello")
    String getHello();
}
