package com.seriousplay.productivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author changqingshun
 */
@SpringBootApplication(scanBasePackages = {"com.seriousplay.productivity"})
@ImportResource({"classpath*:spring-*.xml"})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}