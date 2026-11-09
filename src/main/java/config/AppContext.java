package config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppContext {
    private static AnnotationConfigApplicationContext context;

    public static synchronized <T> T getBean(Class<T> type) {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(SpringConfig.class);
        }
        return context.getBean(type);
    }
}