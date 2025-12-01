package com.study.app_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AppServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServicesApplication.class, args);
	}

}

@RestController
class DefaultController {
    @GetMapping("/")
    public String home() {
        return "Welcome to the app!";
    }
}
