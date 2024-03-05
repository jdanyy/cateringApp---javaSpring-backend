package edu.bbte.idde.jdim2141.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.jdim2141.spring.controller.interceptor.ContentTypeInterceptor;
import edu.bbte.idde.jdim2141.spring.controller.interceptor.LoggerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor());
        registry.addInterceptor(new ContentTypeInterceptor(objectMapper))
            .addPathPatterns("/api/menus");
    }
}
