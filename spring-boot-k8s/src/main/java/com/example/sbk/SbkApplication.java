package com.example.sbk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@SpringBootApplication
public class SbkApplication {

	@GetMapping("/hello")
	String hello() {
		return "Hello World";
	}

	@GetMapping("/now")
	String now() {
		var sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		return sdf.format(new Date()) + " @" + sdf.getTimeZone().getID();
	}

	public static void main(String[] args) {
		SpringApplication.run(SbkApplication.class, args);
	}

}
