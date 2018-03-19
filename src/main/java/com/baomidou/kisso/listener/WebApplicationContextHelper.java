package com.baomidou.kisso.listener;

import com.doctorai.kisso.web.WebKissoConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author ZhaoShihao
 * @version 1.0
 * @create 2018-03-06 15:18
 */
@WebListener
public class WebApplicationContextHelper implements ApplicationContextAware, ServletContextListener {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        WebApplicationContextHelper.context = context;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        new WebKissoConfigurer().initKisso(context);
        WebApplicationContextHelper.context = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
