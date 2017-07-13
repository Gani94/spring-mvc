package com.gani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class SpringmvcApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringmvcApplication.class, args);

		System.out.println("BEANS *********");
		System.out.println("Total bean count is: "+ctx.getBeanDefinitionCount());

		for (String name:ctx.getBeanDefinitionNames()) {

			System.out.println(name);

		}


	}
}
