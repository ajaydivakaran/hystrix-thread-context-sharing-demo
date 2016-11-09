package com.example;

import com.example.reporting.CorrelationHystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.HystrixPlugins;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableHystrix
public class HystrixThreadsApplication {

    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new CorrelationIdInterceptor());
            }
        };
    }

    @PostConstruct
    public void postConstruct() {
        HystrixPlugins.getInstance().registerConcurrencyStrategy(new CorrelationHystrixConcurrencyStrategy());
    }

    public static void main(String[] args) {
        SpringApplication.run(HystrixThreadsApplication.class, args);
    }
}
