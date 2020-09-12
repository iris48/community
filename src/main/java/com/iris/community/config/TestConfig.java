package com.iris.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class TestConfig {

    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    }
    //方法名就是bean的名字
    //方法返回的对象将被装配到容器里
}
