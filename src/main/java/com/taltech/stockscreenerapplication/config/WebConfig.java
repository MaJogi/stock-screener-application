package com.taltech.stockscreenerapplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@PropertySource("classpath:/application.properties")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS", "HEAD")
                .allowedHeaders("*");
    }
}
