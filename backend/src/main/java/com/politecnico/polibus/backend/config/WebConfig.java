package com.politecnico.operacionales.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOWED_ORIGINS = "https://polibus-front-b2195b1b644b.herokuapp.com";
    private static final String[] ALLOWED_METHODS = {"GET", "POST"};
    private static final String[] ALLOWED_HEADERS = {"Authorization", "Content-Type", "Accept"};
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders(ALLOWED_HEADERS)
                .allowCredentials(true)  // Asegura que las credenciales (cookies, Authorization) se permitan
                .exposedHeaders("Authorization"); // Expone el encabezado Authorization, si es necesario
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ALLOWED_ORIGINS)
                        .allowedMethods(ALLOWED_METHODS)
                        .allowedHeaders(ALLOWED_HEADERS)
                        .allowCredentials(true);
            }
        };
    }
}
