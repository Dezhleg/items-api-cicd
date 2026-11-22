package com.example.itemsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ItemsApiApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ItemsApiApplication.class);
        if (Arrays.asList(args).contains("--migrate")) {
            Map<String, Object> props = new HashMap<>();
            props.put("spring.profiles.active", "migrate");
            props.put("spring.main.web-application-type", "none");
            props.put("spring.autoconfigure.exclude",
                    HibernateJpaAutoConfiguration.class.getName() + "," +
                    JpaRepositoriesAutoConfiguration.class.getName());
            app.setDefaultProperties(props);
        }
        app.run(args);
    }
}
