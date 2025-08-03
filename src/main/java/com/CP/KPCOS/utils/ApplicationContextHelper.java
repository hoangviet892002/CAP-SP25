package com.CP.KPCOS.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * Get Spring bean by class type
     */
    public static <T> T getBean(Class<T> beanClass) {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext is not initialized");
        }
        return applicationContext.getBean(beanClass);
    }

    /**
     * Get Spring bean by name
     */
    public static Object getBean(String beanName) {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext is not initialized");
        }
        return applicationContext.getBean(beanName);
    }

    /**
     * Get Spring bean by name and class type
     */
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext is not initialized");
        }
        return applicationContext.getBean(beanName, beanClass);
    }

    /**
     * Check if ApplicationContext is available
     */
    public static boolean isContextAvailable() {
        return applicationContext != null;
    }
}
