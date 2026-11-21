package com.example.itemsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.util.Arrays;

@SpringBootApplication
public class ItemsApiApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ItemsApiApplication.class);
        if (Arrays.asList(args).contains("--migrate")) {
            app.setAdditionalProfiles("migrate");
        }
        app.run(args);
    }
}
