package com.example.sck.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("sbk-app-svc")
public interface SbkClient {

    @GetMapping("/hello")
    String hello();
}
