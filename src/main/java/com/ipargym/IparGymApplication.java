package com.ipargym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/** Punto de entrada de la API IparGym. */
@SpringBootApplication
public class IparGymApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(IparGymApplication.class, args);
    }

    /** Permite desplegar también el WAR en un contenedor servlet externo. */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IparGymApplication.class);
    }
}
