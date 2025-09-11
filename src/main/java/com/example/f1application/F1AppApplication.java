// src/main/java/com/example/f1application/F1AppApplication.java
package com.example.f1application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableScheduling
@EnableAsync
@SpringBootApplication(
    scanBasePackages = {
        "com.example.f1application", // your local package (controllers/config under this root)
        "com.example.f1app"          // existing app code root (controllers, services, config, etc.)
    }
)
@EnableJpaRepositories(basePackages = {
    "com.example.f1app.repository"  // JPA repositories
})
@EntityScan(basePackages = {
    "com.example.f1app.model"       // JPA entities
})
public class F1AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(F1AppApplication.class, args);
    }
}
