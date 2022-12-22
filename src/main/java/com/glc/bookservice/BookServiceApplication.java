package com.glc.bookservice;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication app =new SpringApplication(BookServiceApplication.class);
		app.setDefaultProperties(Collections
		.singletonMap("server.port", "5000"));
	  	app.run(args);
	}

}
