package com.todomeet.todomeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@ComponentScan(basePackages = "com.todomeet")
public class TodomeetApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodomeetApplication.class, args);
	}

}
