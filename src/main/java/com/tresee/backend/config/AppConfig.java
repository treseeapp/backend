package com.tresee.backend.config;


import com.tresee.backend.filter.ProfesorFilter;
import com.tresee.backend.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public TokenFilter getTokenFilter() {
        return new TokenFilter();
    }

    @Bean
    public ProfesorFilter getProfesorFilter() {
        return new ProfesorFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenFilter()).addPathPatterns("/private/**", "/admin/**"); // Este filtro valida el token
        registry.addInterceptor(getTokenFilter()).addPathPatterns("/admin/**"); // Este filtro solo valida que el usuario del token, tenga el rol PROFESOR
    }
}