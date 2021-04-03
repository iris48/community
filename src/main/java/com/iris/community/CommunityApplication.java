package com.iris.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

//@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class CommunityApplication {

	@PostConstruct//管理bean的周期
	public void init(){
		//解决netty启动冲突的问题
		//NettyforUtils
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
