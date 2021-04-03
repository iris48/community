package com.iris.community;

import com.iris.community.entity.Person;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	@Autowired
	private Person person;
	@Autowired
	ApplicationContext ioc;
	@Test
	public void testApplicationContext() {
		System.out.println(applicationContext);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void compare(){
		String s1 = "-1283472332";
		String s2 = String.valueOf(Integer.MIN_VALUE);
		System.out.println(s2.toString());
		System.out.println(s2.compareTo(s1));
		String s3 = "-91";
		System.out.println(Integer.MIN_VALUE);
		System.out.println(Integer.MAX_VALUE);

	}
	@Test
	public void testPerson(){
		//System.out.println(person);
		boolean b = ioc.containsBean("helloService");
		System.out.println(b);
	}


}
