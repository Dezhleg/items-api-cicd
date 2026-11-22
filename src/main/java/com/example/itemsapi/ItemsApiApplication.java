package com.example.itemsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(
    basePackages = "com.example.itemsapi",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = MigratorApplication.class
    )
)
@EnableJpaRepositories(basePackages = "com.example.itemsapi.repository")
@EntityScan(basePackages = "com.example.itemsapi.model")
public class ItemsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemsApiApplication.class, args);
    }
}
