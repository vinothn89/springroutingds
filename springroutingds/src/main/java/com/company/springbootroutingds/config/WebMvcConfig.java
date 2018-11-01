package com.company.springbootroutingds.config;


import com.company.springbootroutingds.SpringbootroutingdsApplication;

import com.company.springbootroutingds.interceptor.DataSourceInterceptor;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EntityScan(basePackageClasses = { SpringbootroutingdsApplication.class})
@Import({SecurityConfig.class })

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataSourceInterceptor())
                            .addPathPatterns("/publisher/*", "/advertiser/*","/database","/database/*");
    }
}
