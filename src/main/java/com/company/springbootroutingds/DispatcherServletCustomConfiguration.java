package com.company.springbootroutingds;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class DispatcherServletCustomConfiguration {

    @Bean
    public DispatcherServlet dispatcherServlet() {
    	 DispatcherServlet dispatcherServlet = new DispatcherServlet();
    	 dispatcherServlet.setThreadContextInheritable(true);
    	 dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    	 return dispatcherServlet;
    }
    
    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet(), "/database");
        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        registration.setAsyncSupported(true);
        return registration;
    }
}
