package com.example.demo.config;

import com.example.demo.interceptor.AuthHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthHandlerInterceptor authHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/docs",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/*",
                        "/openapi.yaml",
                        "/swagger-resources/**",
                        "/swagger-ui/*",
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/register",
                        "/registerGet",
                        "/authorise",
                        "/authoriseGet"
                );
    }
}
