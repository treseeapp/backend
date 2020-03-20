package com.tresee.backend.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

//    @Bean
//    public TokenFilter getFilter() {
//        return new TokenFilter();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
         * Aqui van todos los interceptors que creemos.
         * */

//        registry.addInterceptor(getFilter()).addPathPatterns("/p/**");

    }
}