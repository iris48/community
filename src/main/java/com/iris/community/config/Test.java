package com.iris.community.config;

import com.iris.community.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Test {
    @Bean
    public HelloService helloService(){
        System.out.println("hello");
        return new HelloService();
    }
}
