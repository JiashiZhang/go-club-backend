package com.goclub.xian.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    //cors 跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5175","http://localhost:5174","http://localhost:5173")
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedHeaders("*");
    }

}
