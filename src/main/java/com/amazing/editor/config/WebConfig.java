package com.amazing.editor.config;

import com.amazing.editor.config.intercepors.IndexIntercepor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private IndexIntercepor indexIntercepor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(indexIntercepor).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(true) //设置为true
                .maxAge(3600);
    }
}
