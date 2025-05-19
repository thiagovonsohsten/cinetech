package com.cinetech.apresentacao.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.cinetech.apresentacao.backend",
    "com.cinetech.aplicacao",
    "com.cinetech.infraestrutura",
    "com.cinetech.dominio"
})
@EntityScan(basePackages = {
    "com.cinetech.infraestrutura.persistencia",
    "com.cinetech.dominio"
})
@EnableJpaRepositories(basePackages = {
    "com.cinetech.infraestrutura.persistencia",
    "com.cinetech.dominio"
})
public class CinetechApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinetechApplication.class, args);
    }
} 