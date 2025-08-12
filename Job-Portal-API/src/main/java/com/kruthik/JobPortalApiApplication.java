package com.kruthik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/cloudinary.properties")
public class JobPortalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApiApplication.class, args);
	}

}
