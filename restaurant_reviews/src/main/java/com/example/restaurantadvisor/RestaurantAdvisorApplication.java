package com.example.restaurantadvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.restaurantadvisor.repository")
public class RestaurantAdvisorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantAdvisorApplication.class, args);
    }

}