package com.iris.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class AlphaConfig {
    @Bean//方法名就是bean的名字
    public SimpleDateFormat simpleDateFormat(){

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
