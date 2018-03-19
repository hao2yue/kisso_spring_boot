package com.baomidou.kisso.listener;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.WebAppRootListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author ZhaoShihao
 * @version 1.0
 * @create 2018-03-07 14:15
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class WebAppRootContext implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(WebAppRootListener.class);
        servletContext.setInitParameter("kissoConfigLocation", "classpath:sso.properties");
    }
}
