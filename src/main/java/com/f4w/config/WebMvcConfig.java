package com.f4w.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author admin
 * @date 2018/8/8
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Resource
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
